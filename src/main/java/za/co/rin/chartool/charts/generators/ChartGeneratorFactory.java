package za.co.rin.chartool.charts.generators;

import za.co.rin.chartool.charts.config.ChartDefinition;

public interface ChartGeneratorFactory {

    ChartGenerator getChartGenerator(ChartDefinition config);

}
