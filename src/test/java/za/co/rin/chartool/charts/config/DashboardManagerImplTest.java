package za.co.rin.chartool.charts.config;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import za.co.rin.chartool.generated.Charts;
import za.co.rin.chartool.generated.Dashboard;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

public class DashboardManagerImplTest {

    private DashboardManagerImpl dashboardManager = new DashboardManagerImpl();

    @Before
    public void setUp() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setClassesToBeBound(Charts.class, Dashboard.class);
        jaxb2Marshaller.setSchema(new ClassPathResource("charts.xsd"));

        this.dashboardManager.setUnmarshaller(jaxb2Marshaller);
        this.dashboardManager.initState();
    }

    @Test
     public void testGetDashboardDefinition() throws Exception {
        DashboardDefinition dashboardDefinition = dashboardManager.getDashboardDefinition("test");

        assertThat(dashboardDefinition, is(notNullValue()));
        assertThat(dashboardDefinition.getId(), is(equalTo("test")));
        assertThat(dashboardDefinition.getName(), is(equalTo("Test Dashboard")));
        assertThat(dashboardDefinition.getDescription(), is(equalTo("Test Dashboard Description")));
        assertThat(dashboardDefinition.getCharts().size(), is(equalTo(2)));
    }

    @Test
    public void testGetChartDefinition() throws Exception {
        ChartDefinition chartDefinition = dashboardManager.getChartDefinition("test", "test1");

        assertThat(chartDefinition, is(notNullValue()));
        assertThat(chartDefinition.getId(), is(equalTo("test1")));
        assertThat(chartDefinition.getName(), is(equalTo("Test Chart 1")));
        assertThat(chartDefinition.getDescription(), is(equalTo("Test Chart 1 Description")));
        assertThat(chartDefinition.getType(), is(equalTo("PieChart")));
        assertThat(chartDefinition.getQuery(), is(equalTo("SELECT * FROM TEST1;")));
    }
}