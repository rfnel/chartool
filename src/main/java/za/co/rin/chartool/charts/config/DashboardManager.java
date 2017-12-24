package za.co.rin.chartool.charts.config;

public interface DashboardManager {

    DashboardDefinition getDashboardDefinition(String dashboardId);
    ChartDefinition getChartDefinition(String dashboardId, String chartId);
}
