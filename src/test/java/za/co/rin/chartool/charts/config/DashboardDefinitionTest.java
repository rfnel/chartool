package za.co.rin.chartool.charts.config;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DashboardDefinitionTest {

    @Test
    public void testChartsByRow() {
        DashboardDefinition dashboardDefinition = new DashboardDefinition();
        dashboardDefinition.addChart(new ChartDefinition());
        dashboardDefinition.addChart(new ChartDefinition());
        dashboardDefinition.addChart(new ChartDefinition());

        assertThat(dashboardDefinition.getChartsByRow().size(), is(equalTo(2)));

    }

}