package za.co.rin.chartool.charts.controllers;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import za.co.rin.chartool.charts.config.ChartDefinition;
import za.co.rin.chartool.charts.generators.ChartGenerator;
import za.co.rin.chartool.charts.generators.ChartGeneratorFactory;
import za.co.rin.chartool.generated.ChartType;
import za.co.rin.chartool.generated.Charts;

import javax.annotation.PostConstruct;
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
    private Map<String, ChartDefinition> chartDefinitions;

    @PostConstruct
    public void loadChartDefinitions() {
        this.chartDefinitions = new HashMap<>();

        Charts charts = null;
        try {
            charts = (Charts) unmarshaller.unmarshal(new StreamSource(this.getClass().getClassLoader().getResourceAsStream("charts.xml")));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load chart definitions.");
        }

        for (ChartType chart : charts.getChart()) {
            ChartDefinition chartDefinition = new ChartDefinition();
            chartDefinition.setType(chart.getType());
            chartDefinition.setName(chart.getName());
            chartDefinition.setQuery(chart.getQuery());

            chartDefinitions.put(chart.getName(), chartDefinition);
        }
    }

    @RequestMapping("/chart")
    @ResponseBody
    public ResponseEntity<byte[]> requestChart(@RequestParam String name) {
        ChartDefinition chartDefinition = getChartDefinition(name);

        ChartGenerator chartGenerator = chartGeneratorFactory.getChartGenerator(chartDefinition);
        JFreeChart jFreeChart = chartGenerator.generateChart(chartDefinition);

        return createResponse(jFreeChart);
    }

    @RequestMapping("/allCharts")
    @ResponseBody
    public String requestAllCharts() {
        StringBuilder chartsHtml = new StringBuilder();
        for (ChartDefinition chartDefinition : chartDefinitions.values()) {
            chartsHtml.append("        <img src=\"chart?name=" + chartDefinition.getName() + "\"/>");
        }

        String responseHtml = "<html>\n" +
                "    <head>\n" +
                "        <title>Charts</title>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <h1>Chart Dashboard</h1>\n" +
                chartsHtml +
                "    </body>\n" +
                "</html>";

        return responseHtml;
    }

    private ResponseEntity<byte[]> createResponse(JFreeChart jFreeChart) {
        byte[] image = chartToImage(jFreeChart);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    private ChartDefinition getChartDefinition(String name) {
        return chartDefinitions.get(name);
    }

    private byte[] chartToImage(JFreeChart jFreeChart) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ChartUtilities.writeChartAsPNG(baos, jFreeChart, 800, 600);

            return baos.toByteArray();
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to create image from chart.");
        }
    }
}
