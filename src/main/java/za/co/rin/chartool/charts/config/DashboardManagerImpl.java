package za.co.rin.chartool.charts.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Component;
import za.co.rin.chartool.generated.Chart;
import za.co.rin.chartool.generated.Charts;
import za.co.rin.chartool.generated.Dashboard;

import javax.annotation.PostConstruct;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class DashboardManagerImpl implements DashboardManager {

    @Autowired
    private Unmarshaller unmarshaller;

    private Map<String, DashboardDefinition> dashboardDefinitions;

    @PostConstruct
    public void initState() {
        this.dashboardDefinitions = loadDashboardDefinitions();
    }

    public DashboardDefinition getDashboardDefinition(String dashboardId) {
        return dashboardDefinitions.get(dashboardId);
    }

    public ChartDefinition getChartDefinition(String dashboardId, String chartId) {
        DashboardDefinition dashboardDefinition = getDashboardDefinition(dashboardId);
        for (ChartDefinition chartDefinition : dashboardDefinition.getCharts()) {
            if (chartId.equals(chartDefinition.getId())) {
                return chartDefinition;
            }
        }

        return null;
    }

    private Map<String, DashboardDefinition> loadDashboardDefinitions() {
        Map<String, DashboardDefinition> dashboardDefinitions = new HashMap<>();

        Charts charts = loadChartsFromXml();

        for (Dashboard dashboard : charts.getDashboard()) {
            loadDashboardDefinition(dashboardDefinitions, dashboard);
        }

        return dashboardDefinitions;
    }

    private void loadDashboardDefinition(Map<String, DashboardDefinition> dashboardDefinitions, Dashboard dashboard) {
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
        chartDefinition.setLabel(chart.getLabel());
        chartDefinition.setQuery(chart.getQuery());

        return chartDefinition;
    }

    private Charts loadChartsFromXml() {
        Charts charts;
        try {
            charts = (Charts) unmarshaller.unmarshal(new StreamSource(this.getClass().getClassLoader().getResourceAsStream("charts.xml")));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load chart definitions.");
        }
        return charts;
    }

    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }
}
