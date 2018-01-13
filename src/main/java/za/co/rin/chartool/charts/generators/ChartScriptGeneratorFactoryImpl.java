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
    @Autowired
    private BarChartScriptGenerator barChartScriptGenerator;
    @Autowired
    private ScatterChartScriptGenerator scatterChartScriptGenerator;
    @Autowired
    private RadarChartScriptGenerator radarChartScriptGenerator;

    @Override
    public ChartScriptGenerator getChartScriptGenerator(ChartDefinition chartDefinition) {
        switch (chartDefinition.getType()) {
            case "PieChart":
                return pieChartScriptGenerator;
            case "LineChart":
                return lineChartScriptGenerator;
            case "BarChart":
                return barChartScriptGenerator;
            case "ScatterChart":
                return scatterChartScriptGenerator;
            case "RadarChart":
                return radarChartScriptGenerator;

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

    protected void setBarChartScriptGenerator(BarChartScriptGenerator barChartScriptGenerator) {
        this.barChartScriptGenerator = barChartScriptGenerator;
    }

    protected void setScatterChartScriptGenerator(ScatterChartScriptGenerator scatterChartScriptGenerator) {
        this.scatterChartScriptGenerator = scatterChartScriptGenerator;
    }

    public void setRadarChartScriptGenerator(RadarChartScriptGenerator radarChartScriptGenerator) {
        this.radarChartScriptGenerator = radarChartScriptGenerator;
    }
}
