package za.co.rin.chartool.charts.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DashboardDefinition {

    private String id;
    private String name;
    private String description;

    private List<ChartDefinition> charts = new ArrayList<>();

    public DashboardDefinition() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addChart(ChartDefinition chart) {
        this.charts.add(chart);
    }

    public List<ChartDefinition> getCharts() {
        return Collections.unmodifiableList(charts);
    }
}
