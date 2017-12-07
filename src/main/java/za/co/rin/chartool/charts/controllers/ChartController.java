package za.co.rin.chartool.charts.controllers;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.PostConstruct;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.Unmarshaller;
import za.co.rin.chartool.charts.config.ChartDefinition;
import za.co.rin.chartool.charts.config.DashboardDefinition;
import za.co.rin.chartool.charts.generators.ChartGenerator;
import za.co.rin.chartool.charts.generators.ChartGeneratorFactory;
import za.co.rin.chartool.generated.Chart;
import za.co.rin.chartool.generated.Charts;
import za.co.rin.chartool.generated.Dashboard;

import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ChartController {

    //TODO:  Split this into multiple classes.

    @Autowired
    private ChartGeneratorFactory chartGeneratorFactory;

    @Autowired
    private Unmarshaller unmarshaller;

    private Map<String, DashboardDefinition> dashboardDefinitions;

    @PostConstruct
    public void initState() {
        this.dashboardDefinitions = loadDashboardDefinitions();
    }

    @RequestMapping("/chart")
    @ResponseBody
    public ResponseEntity<byte[]>  requestChart(@RequestParam String dashboardId, @RequestParam String chartId) {
        ChartDefinition chartDefinition = getChartDefinition(dashboardId, chartId);

        ChartGenerator chartGenerator = chartGeneratorFactory.getChartGenerator(chartDefinition);
        JFreeChart jFreeChart = chartGenerator.generateChart(chartDefinition);

        return createResponse(jFreeChart);
    }

    @RequestMapping("/dashboard")
    @ResponseBody
    public String requestDashBoard(@RequestParam String id) {
        StringBuilder chartsHtml = new StringBuilder();
        DashboardDefinition dashboardDefinition =  getDashboardDefinition(id);
        for (ChartDefinition chartDefinition : dashboardDefinition.getCharts()) {
            chartsHtml.append("        <img src=\"chart?dashboardId=" + dashboardDefinition.getId() + "&chartId=" + chartDefinition.getId() + "\"/><br /><br />");
        }

        String responseHtml = "<html>\n" +
                "    <head>\n" +
                "        <title>" + dashboardDefinition.getName() + "</title>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <h1>" + dashboardDefinition.getName() + "</h1>\n" +
                "        <p>" + dashboardDefinition.getDescription() + "</p>" +
        chartsHtml +
                "    </body>\n" +
                "</html>";

        return responseHtml;
    }

    protected Map<String, DashboardDefinition> loadDashboardDefinitions() {
        Map<String, DashboardDefinition> dashboardDefinitions = new HashMap<>();

        Charts charts = loadChartsFromXml();

        for (Dashboard dashboard : charts.getDashboard()) {
            loadDashboardDefition(dashboardDefinitions, dashboard);
        }

        return dashboardDefinitions;
    }

    private void loadDashboardDefition(Map<String, DashboardDefinition> dashboardDefinitions, Dashboard dashboard) {
        DashboardDefinition dashboardDefinition = new DashboardDefinition();
        dashboardDefinition.setId(dashboard.getId());
        dashboardDefinition.setName(dashboard.getName());
        dashboardDefinition.setDescription(dashboard.getDescription());

        for (Chart chart : dashboard.getChart()) {
            ChartDefinition chartDefinition = loadChartDefinition(chart);

            dashboardDefinition.addChart(chartDefinition);
        }

        dashboardDefinitions.put(dashboard.getId(), dashboardDefinition);
    }

    private ChartDefinition loadChartDefinition(Chart chart) {
        ChartDefinition chartDefinition = new ChartDefinition();

        chartDefinition.setId(chart.getId());
        chartDefinition.setName(chart.getName());
        chartDefinition.setDescription(chart.getDescription());
        chartDefinition.setType(chart.getType());
        chartDefinition.setQuery(chart.getQuery());
        return chartDefinition;
    }

    private Charts loadChartsFromXml() {
        Charts charts = null;
        try {
            charts = (Charts) unmarshaller.unmarshal(new StreamSource(this.getClass().getClassLoader().getResourceAsStream("charts.xml")));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load chart definitions.");
        }
        return charts;
    }

    private ChartDefinition getChartDefinition(String dashboardId, String chartId) {
        //TODO:  Optimize to load charts into map for each dashboard.
        DashboardDefinition dashboardDefinition = getDashboardDefinition(dashboardId);
        for (ChartDefinition chartDefinition : dashboardDefinition.getCharts()) {
            if (chartId.equals(chartDefinition.getId())) {
                return chartDefinition;
            }
        }

        return null;
    }

    private ResponseEntity<byte[]> createResponse(JFreeChart jFreeChart) {
        byte[] image = chartToImage(jFreeChart);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    private DashboardDefinition getDashboardDefinition(String id) {
        return dashboardDefinitions.get(id);
    }

    private byte[] chartToImage(JFreeChart jFreeChart) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ChartUtilities.writeChartAsPNG(baos, jFreeChart, 800, 600);

            return baos.toByteArray();
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to create image from chart.");
        }
    }

    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

    public void setChartGeneratorFactory(ChartGeneratorFactory chartGeneratorFactory) {
        this.chartGeneratorFactory = chartGeneratorFactory;
    }
}
