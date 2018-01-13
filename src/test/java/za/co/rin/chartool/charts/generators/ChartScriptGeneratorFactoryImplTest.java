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

        KeyValueChartScriptGenerator keyValueChartScriptGenerator = new KeyValueChartScriptGenerator();

        this.chartScriptGeneratorFactory.setKeyValueChartScriptGenerator(keyValueChartScriptGenerator);
        this.chartScriptGeneratorFactory.setPointChartScriptGenerator(new PointChartScriptGenerator());
    }

    @Test
    public void testGetChartGeneratorForPieChart() throws Exception {
        ChartDefinition chartDefinition = getChartConfig("PieChart");
        ChartScriptGenerator chartGenerator = chartScriptGeneratorFactory.getChartScriptGenerator(chartDefinition);

        assertThat(chartGenerator, is(instanceOf(KeyValueChartScriptGenerator.class)));
    }

    @Test
    public void testGetChartGeneratorForLineChart() throws Exception {
        ChartDefinition chartDefinition = getChartConfig("LineChart");
        ChartScriptGenerator chartGenerator = chartScriptGeneratorFactory.getChartScriptGenerator(chartDefinition);

        assertThat(chartGenerator, is(Matchers.instanceOf(KeyValueChartScriptGenerator.class)));
    }

    @Test
    public void testGetChartGeneratorForBarChart() throws Exception {
        ChartDefinition chartDefinition = getChartConfig("BarChart");
        ChartScriptGenerator chartGenerator = chartScriptGeneratorFactory.getChartScriptGenerator(chartDefinition);

        assertThat(chartGenerator, is(Matchers.instanceOf(KeyValueChartScriptGenerator.class)));
    }

    @Test
    public void testGetChartGeneratorForScatterChart() throws Exception {
        ChartDefinition chartDefinition = getChartConfig("ScatterChart");
        ChartScriptGenerator chartGenerator = chartScriptGeneratorFactory.getChartScriptGenerator(chartDefinition);

        assertThat(chartGenerator, is(Matchers.instanceOf(PointChartScriptGenerator.class)));
    }

    @Test
    public void testGetChartGeneratorForRadarChart() throws Exception {
        ChartDefinition chartDefinition = getChartConfig("RadarChart");
        ChartScriptGenerator chartGenerator = chartScriptGeneratorFactory.getChartScriptGenerator(chartDefinition);

        assertThat(chartGenerator, is(Matchers.instanceOf(KeyValueChartScriptGenerator.class)));
    }

    private ChartDefinition getChartConfig(String type) {
        ChartDefinition chartDefinition = new ChartDefinition();

        chartDefinition.setType(type);

        return chartDefinition;
    }
}