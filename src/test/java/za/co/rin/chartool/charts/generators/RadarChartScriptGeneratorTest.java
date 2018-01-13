package za.co.rin.chartool.charts.generators;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import za.co.rin.chartool.charts.colors.ChartColorManager;
import za.co.rin.chartool.charts.config.ChartDefinition;
import za.co.rin.chartool.charts.data.ChartDataSource;
import za.co.rin.chartool.charts.data.KeyValueDataItem;
import za.co.rin.chartool.charts.templates.TemplateManagerImpl;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class RadarChartScriptGeneratorTest {

    private RadarChartScriptGenerator radarChartScriptGenerator = new RadarChartScriptGenerator();

    private JUnit4Mockery context = new JUnit4Mockery();
    private ChartColorManager chartColorManagerMock;
    private ChartDataSource chartDataSourceMock;

    private static final String TEST_COLORS = "'rgba(0, 0, 139, 0.5)', 'rgba(54, 162, 235, 0.5)'";

    @Before
    public void setUp() {
        chartColorManagerMock = context.mock(ChartColorManager.class);
        chartDataSourceMock = context.mock(ChartDataSource.class);

        radarChartScriptGenerator.setChartColorManager(chartColorManagerMock);
        radarChartScriptGenerator.setChartDataSource(chartDataSourceMock);
        radarChartScriptGenerator.setTemplateManager(new TemplateManagerImpl());
    }

    @Test
    public void testGetChartScript() throws Exception {
        ChartDefinition testChartDefinition = getTestChartDefinition();
        List<KeyValueDataItem> testDataItems = getTestDataItems();

        context.checking(new Expectations() {{
            oneOf(chartDataSourceMock).getKeyValueDataItems(testChartDefinition);
            will(returnValue(testDataItems));
            oneOf(chartColorManagerMock).getChartColorsJson(1, 2);
            will(returnValue(TEST_COLORS));

        }});

        String chartScript = radarChartScriptGenerator.getChartScript(testChartDefinition);
        assertThat(chartScript, containsString("function load_test_chart()"));
        assertThat(chartScript, containsString("type: 'radar',"));
        assertThat(chartScript, containsString("label: 'Test Chart Label'"));
        assertThat(chartScript, containsString("data: [1,2]"));
        assertThat(chartScript, containsString("backgroundColor: [" + TEST_COLORS + "]"));
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

    private List<KeyValueDataItem> getTestDataItems() {
        List<KeyValueDataItem> dataItems = new ArrayList<>();

        dataItems.add(new KeyValueDataItem("One", 1));
        dataItems.add(new KeyValueDataItem("Two", 2));

        return dataItems;
    }
}