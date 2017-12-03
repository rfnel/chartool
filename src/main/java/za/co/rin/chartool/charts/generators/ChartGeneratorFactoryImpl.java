package za.co.rin.chartool.charts.generators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.rin.chartool.charts.config.ChartDefinition;

@Component
public class ChartGeneratorFactoryImpl implements ChartGeneratorFactory {

    @Autowired
    private PieChartGenerator pieChartGenerator;

    @Override
    public ChartGenerator getChartGenerator(ChartDefinition config) {

        switch (config.getType()) {
            case "PieChart":
                return pieChartGenerator;

            default:
                throw new IllegalArgumentException("Unable to retrieve chart generator for: " + config.getType());
        }
    }

    void setPieChartGenerator(PieChartGenerator pieChartGenerator) {
        this.pieChartGenerator = pieChartGenerator;
    }
}
