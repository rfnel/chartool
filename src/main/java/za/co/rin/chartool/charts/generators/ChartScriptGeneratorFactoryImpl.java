package za.co.rin.chartool.charts.generators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.rin.chartool.charts.config.ChartDefinition;

@Component
public class ChartScriptGeneratorFactoryImpl implements ChartScriptGeneratorFactory {

    @Autowired
    private PieChartScriptGenerator pieChartScriptGenerator;
    @Autowired
    private LineChartScriptGenerator lineChartScriptGenerator;

    @Override
    public ChartScriptGenerator getChartScriptGenerator(ChartDefinition chartDefinition) {
        switch (chartDefinition.getType()) {
            case "PieChart":
                return pieChartScriptGenerator;
            case "LineChart":
                return lineChartScriptGenerator;
            default:
                throw new IllegalArgumentException("No chart script generator found for type: " + chartDefinition.getType());
        }
    }

    protected void setPieChartScriptGenerator(PieChartScriptGenerator pieChartScriptGenerator) {
        this.pieChartScriptGenerator = pieChartScriptGenerator;
    }

    protected void setLineChartScriptGenerator(LineChartScriptGenerator lineChartScriptGenerator) {
        this.lineChartScriptGenerator = lineChartScriptGenerator;
    }
}
