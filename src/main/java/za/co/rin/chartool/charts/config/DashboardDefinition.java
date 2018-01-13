package za.co.rin.chartool.charts.config;

import java.util.ArrayList;
import java.util.List;

public class DashboardDefinition {

    private String id;
    private String name;
    private String description;

    private final List<ChartDefinition> charts = new ArrayList<>();

    //Used for layout.
    private final List<List<ChartDefinition>> chartsByRow = new ArrayList<>();

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

        List<ChartDefinition> chartsInRow;
        if (this.charts.size() % 2 == 1) {
            chartsInRow = new ArrayList<>();
            chartsByRow.add(chartsInRow);
        } else {
            chartsInRow = chartsByRow.get(chartsByRow.size() - 1);
        }

        chartsInRow.add(chart);
    }

    public List<ChartDefinition> getCharts() {
        return charts;
    }

    public List<List<ChartDefinition>> getChartsByRow() {
        return chartsByRow;
    }
}
