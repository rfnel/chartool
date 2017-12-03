package za.co.rin.chartool.charts.generators;

import org.junit.Before;
import org.junit.Test;
import za.co.rin.chartool.charts.config.ChartDefinition;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ChartGeneratorFactoryImplTest {

    private ChartGeneratorFactoryImpl chartGeneratorFactory;

    @Before
    public void setUp() {
        this.chartGeneratorFactory  = new ChartGeneratorFactoryImpl();

        chartGeneratorFactory.setPieChartGenerator(new PieChartGenerator());
        chartGeneratorFactory.setLineChartGenerator(new LineChartGenerator());
    }

    @Test
    public void testGetChartGeneratorForPieChart() throws Exception {
        ChartGenerator chartGenerator = chartGeneratorFactory.getChartGenerator(getChartConfig("PieChart"));

        assertThat(chartGenerator.getClass(), is(equalTo(PieChartGenerator.class)));
    }

    @Test
    public void testGetChartGeneratorForLineChart() throws Exception {
        ChartGenerator chartGenerator = chartGeneratorFactory.getChartGenerator(getChartConfig("LineChart"));

        assertThat(chartGenerator.getClass(), is(equalTo(LineChartGenerator.class)));
    }

    private ChartDefinition getChartConfig(String type) {
        ChartDefinition chartDefinition = new ChartDefinition();

        chartDefinition.setType(type);

        return chartDefinition;
    }
}