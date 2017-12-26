package za.co.rin.chartool.charts.colors;

import org.junit.Before;
import org.junit.Test;
import za.co.rin.chartool.charts.colors.ChartColorManagerImpl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ChartColorManagerImplTest {

    private ChartColorManagerImpl chartColorManager = new ChartColorManagerImpl();

    @Before
    public void setUp() {
        chartColorManager.initChartColorList();
    }

    @Test
    public void testGetChartColorsWithFewerColorsThanList() throws Exception {
        String expectedResult = "'rgba(0, 0, 139, 0.5)','rgba(54, 162, 235, 0.5)'";

        String chartColors = chartColorManager.getChartColorsJson(2);

        assertThat(chartColors, is(equalTo(expectedResult)));
    }

    @Test
    public void testGetChartColorsWithMoreColorsThanList() throws Exception {
        String expectedResult = "'rgba(0, 0, 139, 0.5)','rgba(54, 162, 235, 0.5)','rgba(255, 206, 86, 0.5)'";

        String chartColors = chartColorManager.getChartColorsJson(5);

        assertThat(chartColors, is(equalTo(expectedResult)));
    }
}