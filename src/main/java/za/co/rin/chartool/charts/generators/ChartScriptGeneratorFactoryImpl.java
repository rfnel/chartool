package za.co.rin.chartool.charts.generators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.rin.chartool.charts.config.ChartDefinition;

@Component
public class ChartScriptGeneratorFactoryImpl implements ChartScriptGeneratorFactory {

    @Autowired
    private KeyValueChartScriptGenerator keyValueChartScriptGenerator;
    @Autowired
    private PointChartScriptGenerator pointChartScriptGenerator;

    @Override
    public ChartScriptGenerator getChartScriptGenerator(ChartDefinition chartDefinition) {
        switch (chartDefinition.getType()) {
            case "LineChart":
            case "PieChart":
            case "BarChart":
            case "RadarChart":
                return keyValueChartScriptGenerator;
            case "ScatterChart":
                return pointChartScriptGenerator;

            default:
                throw new IllegalArgumentException("No chart script generator found for type: " + chartDefinition.getType());
        }
    }

    protected void setKeyValueChartScriptGenerator(KeyValueChartScriptGenerator keyValueChartScriptGenerator) {
        this.keyValueChartScriptGenerator = keyValueChartScriptGenerator;
    }

    protected void setPointChartScriptGenerator(PointChartScriptGenerator pointChartScriptGenerator) {
        this.pointChartScriptGenerator = pointChartScriptGenerator;
    }
}
