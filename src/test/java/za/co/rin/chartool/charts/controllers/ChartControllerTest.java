package za.co.rin.chartool.charts.controllers;

import org.jfree.chart.ChartFactory;
import org.jfree.data.general.DefaultPieDataset;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import za.co.rin.chartool.charts.config.ChartDefinition;
import za.co.rin.chartool.charts.config.DashboardDefinition;
import za.co.rin.chartool.charts.generators.ChartGenerator;
import za.co.rin.chartool.charts.generators.ChartGeneratorFactory;
import za.co.rin.chartool.generated.Charts;
import za.co.rin.chartool.generated.Dashboard;

import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class ChartControllerTest {

    //TODO:  Add Spring MVC integration test.

    private ChartController chartController;

    private ChartGeneratorFactory chartGeneratorFactoryMock;
    private ChartGenerator chartGeneratorMock;

    private JUnit4Mockery context = new JUnit4Mockery();

    @Before
    public void setUp() {
        this.chartGeneratorFactoryMock = context.mock(ChartGeneratorFactory.class);
        this.chartGeneratorMock = context.mock(ChartGenerator.class);

        this.chartController = new ChartController();
        chartController.setChartGeneratorFactory(chartGeneratorFactoryMock);

        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setClassesToBeBound(Charts.class, Dashboard.class);
        jaxb2Marshaller.setSchema(new ClassPathResource("charts.xsd"));
        chartController.setUnmarshaller(jaxb2Marshaller);

        chartController.initState();
    }

    @Test
    public void testThatDashboardDefinitionsAreLoaded() {
        Map<String, DashboardDefinition> dashboardDefinitions = chartController.loadDashboardDefinitions();

        assertThat(dashboardDefinitions.size(), is(equalTo(2)));
    }
    
    @Test
    public void testThatDashboardIsReturned() {
        ExtendedModelMap model = new ExtendedModelMap();
        String dashBoard = chartController.requestDashBoard("test1", model);
        
        assertThat(dashBoard, is(equalTo("dashboard")));
        assertThat(model.get("dashboard"), is(notNullValue()));
    }

    @Test
    public void testThatChartIsReturned() {
        context.checking(new Expectations() {{
            oneOf(chartGeneratorFactoryMock).getChartGenerator(with(any(ChartDefinition.class)));
            will(returnValue(chartGeneratorMock));

            oneOf(chartGeneratorMock).generateChart(with(any(ChartDefinition.class)));
            will(returnValue(ChartFactory.createPieChart("Test Chart", new DefaultPieDataset())));
        }});

        ResponseEntity<byte[]> responseEntity = chartController.requestChart("test1", "mp");

        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.OK)));
        assertThat(responseEntity.getHeaders().getContentType(), is(equalTo(MediaType.IMAGE_PNG)));
        assertThat(responseEntity.getBody().getClass(), is(equalTo(byte[].class)));
    }

    @After
    public void tearDown() {
        context.assertIsSatisfied();
    }

}
