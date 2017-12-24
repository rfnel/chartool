package za.co.rin.chartool.charts.generators;

import za.co.rin.chartool.charts.config.ChartDefinition;

public interface ChartScriptGeneratorFactory {

    ChartScriptGenerator getChartScriptGenerator(ChartDefinition chartDefinition);
}
