package za.co.rin.chartool.charts.generators;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import za.co.rin.chartool.charts.config.ChartDefinition;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ChartScriptGeneratorFactoryImplTest {

    private ChartScriptGeneratorFactoryImpl chartScriptGeneratorFactory = new ChartScriptGeneratorFactoryImpl();

    @Before
    public void setUp() {
        this.chartScriptGeneratorFactory  = new ChartScriptGeneratorFactoryImpl();

        this.chartScriptGeneratorFactory.setPieChartScriptGenerator(new PieChartScriptGenerator());
        this.chartScriptGeneratorFactory.setLineChartScriptGenerator(new LineChartScriptGenerator());
        this.chartScriptGeneratorFactory.setBarChartScriptGenerator(new BarChartScriptGenerator());
    }

    @Test
    public void testGetChartGeneratorForPieChart() throws Exception {
        ChartDefinition chartDefinition = getChartConfig("PieChart");
        ChartScriptGenerator chartGenerator = chartScriptGeneratorFactory.getChartScriptGenerator(chartDefinition);

        assertThat(chartGenerator, is(instanceOf(PieChartScriptGenerator.class)));
    }

    @Test
    public void testGetChartGeneratorForLineChart() throws Exception {
        ChartDefinition chartDefinition = getChartConfig("LineChart");
        ChartScriptGenerator chartGenerator = chartScriptGeneratorFactory.getChartScriptGenerator(chartDefinition);

        assertThat(chartGenerator, is(Matchers.instanceOf(LineChartScriptGenerator.class)));
    }

    @Test
    public void testGetChartGeneratorForBarChart() throws Exception {
        ChartDefinition chartDefinition = getChartConfig("BarChart");
        ChartScriptGenerator chartGenerator = chartScriptGeneratorFactory.getChartScriptGenerator(chartDefinition);

        assertThat(chartGenerator, is(Matchers.instanceOf(BarChartScriptGenerator.class)));
    }

    private ChartDefinition getChartConfig(String type) {
        ChartDefinition chartDefinition = new ChartDefinition();

        chartDefinition.setType(type);

        return chartDefinition;
    }
}