package za.co.rin.chartool.charts.generators;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import za.co.rin.chartool.charts.colors.ChartColorManager;
import za.co.rin.chartool.charts.config.ChartDefinition;
import za.co.rin.chartool.charts.datasource.ChartDataSource;
import za.co.rin.chartool.charts.datasource.PointDataItem;
import za.co.rin.chartool.charts.templates.TemplateManagerImpl;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class ScatterChartScriptGeneratorTest {

    private ScatterChartScriptGenerator scatterChartScriptGenerator = new ScatterChartScriptGenerator();

    private JUnit4Mockery context = new JUnit4Mockery();
    private ChartColorManager chartColorManagerMock;
    private ChartDataSource chartDataSourceMock;

    private static final String TEST_COLOR = "'rgba(0, 0, 139, 0.5)'";

    @Before
    public void setUp() {
        chartColorManagerMock = context.mock(ChartColorManager.class);
        chartDataSourceMock = context.mock(ChartDataSource.class);

        scatterChartScriptGenerator.setChartColorManager(chartColorManagerMock);
        scatterChartScriptGenerator.setChartDataSource(chartDataSourceMock);
        scatterChartScriptGenerator.setTemplateManager(new TemplateManagerImpl());
    }

    @Test
    public void testGetChartScript() throws Exception {
        ChartDefinition testChartDefinition = getTestChartDefinition();
        List<PointDataItem> testDataItems = getTestDataItems();

        context.checking(new Expectations() {{
            oneOf(chartDataSourceMock).getPointDataItems(testChartDefinition);
            will(returnValue(testDataItems));
            oneOf(chartColorManagerMock).getChartColorsJson(1, 1);
            will(returnValue(TEST_COLOR));

        }});

        String chartScript = scatterChartScriptGenerator.getChartScript(testChartDefinition);
        assertThat(chartScript, containsString("function load_test_chart()"));
        assertThat(chartScript, containsString("type: 'scatter',"));
        assertThat(chartScript, containsString("label: 'Test Chart Label'"));
        assertThat(chartScript, containsString("data: [{x: 1, y: 2},{x: 2, y: 1}]"));
        assertThat(chartScript, containsString("backgroundColor: " + TEST_COLOR));
        assertThat(chartScript, containsString(" document.getElementById(\"test\")"));
        assertThat(chartScript, containsString("text: 'Test Chart',"));

    }

    @After
    public void tearDown() {
        context.assertIsSatisfied();
    }

    private ChartDefinition getTestChartDefinition() {
        ChartDefinition chartDefinition = new ChartDefinition();
        chartDefinition.setId("test");
        chartDefinition.setName("Test Chart");
        chartDefinition.setDescription("Test Chart Description");
        chartDefinition.setLabel("Test Chart Label");
        chartDefinition.setIndex(1);

        return chartDefinition;
    }

    private List<PointDataItem> getTestDataItems() {
        List<PointDataItem> dataItems = new ArrayList<>();

        dataItems.add(new PointDataItem(1, 2));
        dataItems.add(new PointDataItem(2, 1));

        return dataItems;
    }
}