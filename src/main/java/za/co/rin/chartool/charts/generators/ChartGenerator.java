package za.co.rin.chartool.charts.generators;

import org.jfree.chart.JFreeChart;
import za.co.rin.chartool.charts.config.ChartDefinition;

public interface ChartGenerator {

    public JFreeChart generateChart(ChartDefinition chartDefinition);
}
