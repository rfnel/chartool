package za.co.rin.chartool.charts.generators;

import za.co.rin.chartool.charts.config.ChartDefinition;

import java.util.HashMap;
import java.util.Map;

public class ChartSettings {

    public enum ColorStrategy {SINGLE_COLOR, MULTIPLE_COLORS}

    private final String chartScriptTemplate;
    private final String chartDatasetTemplate;
    private final ColorStrategy colorStrategy;

    private static Map<String, ChartSettings> CHART_SETTINGS = new HashMap<>();
    static {
        CHART_SETTINGS.put("BarChart", new ChartSettings("js_templates/bar_charts/bar_chart.template", "js_templates/bar_charts/bar_chart_dataset.template", ColorStrategy.SINGLE_COLOR));
        CHART_SETTINGS.put("LineChart", new ChartSettings("js_templates/line_charts/line_chart.template", "js_templates/line_charts/line_chart_dataset.template", ColorStrategy.SINGLE_COLOR));
        CHART_SETTINGS.put("PieChart", new ChartSettings("js_templates/pie_charts/pie_chart.template", "js_templates/pie_charts/pie_chart_dataset.template", ColorStrategy.MULTIPLE_COLORS));
        CHART_SETTINGS.put("RadarChart", new ChartSettings("js_templates/radar_charts/radar_chart.template", "js_templates/radar_charts/radar_chart_dataset.template", ColorStrategy.SINGLE_COLOR));
        CHART_SETTINGS.put("ScatterChart", new ChartSettings("js_templates/scatter_charts/scatter_chart.template", "js_templates/scatter_charts/scatter_chart_dataset.template", ColorStrategy.SINGLE_COLOR));
    }

    public ChartSettings(String chartScriptTemplate, String chartDatasetTemplate, ColorStrategy colorStrategy) {
        this.chartScriptTemplate = chartScriptTemplate;
        this.chartDatasetTemplate = chartDatasetTemplate;
        this.colorStrategy = colorStrategy;
    }

    public String getChartScriptTemplate() {
        return chartScriptTemplate;
    }

    public String getChartDatasetTemplate() {
        return chartDatasetTemplate;
    }

    public ColorStrategy getColorStrategy() {
        return colorStrategy;
    }

    public static ChartSettings getSettings(ChartDefinition chartDefinition) {
        return CHART_SETTINGS.get(chartDefinition.getType());
    }
}
