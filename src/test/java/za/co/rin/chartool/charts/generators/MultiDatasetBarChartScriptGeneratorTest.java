package za.co.rin.chartool.charts.generators;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import za.co.rin.chartool.charts.colors.ChartColorManager;
import za.co.rin.chartool.charts.config.ChartDefinition;
import za.co.rin.chartool.charts.datasource.ChartDataSource;
import za.co.rin.chartool.charts.datasource.KeyValueDataItem;
import za.co.rin.chartool.charts.datasource.LabeledKeyValueDataItem;
import za.co.rin.chartool.charts.templates.ScriptTemplate;
import za.co.rin.chartool.charts.templates.TemplateManagerImpl;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class MultiDatasetBarChartScriptGeneratorTest {

    private MultiDatasetBarChartScriptGenerator multiDatasetBarChartScriptGenerator = new MultiDatasetBarChartScriptGenerator();

    private JUnit4Mockery context = new JUnit4Mockery();
    private ChartColorManager chartColorManagerMock;
    private ChartDataSource chartDataSourceMock;
    private BarChartDatasetTemplateMapper barChartDatasetTemplateMapperMock;

    private static final String TEST_COLOR = "'rgba(0, 0, 139, 0.5)'";

    @Before
    public void setUp() {
        chartColorManagerMock = context.mock(ChartColorManager.class);
        chartDataSourceMock = context.mock(ChartDataSource.class);

        multiDatasetBarChartScriptGenerator.setChartDataSource(chartDataSourceMock);
        multiDatasetBarChartScriptGenerator.setTemplateManager(new TemplateManagerImpl());
        multiDatasetBarChartScriptGenerator.setBarChartDatasetTemplateMapper(new BarChartDatasetTemplateMapper(chartColorManagerMock));
    }

    @Test
    public void testGetChartScript() throws Exception {
        ChartDefinition testChartDefinition = getTestChartDefinition();
        List<LabeledKeyValueDataItem> testDataItems = getTestDataItems();

        context.checking(new Expectations() {{
            oneOf(chartDataSourceMock).getLabeledKeyValueDataItems(testChartDefinition);
            will(returnValue(testDataItems));
            oneOf(chartColorManagerMock).getChartColorsJson(1, 1);
            will(returnValue(TEST_COLOR));

        }});

        String chartScript = multiDatasetBarChartScriptGenerator.getChartScript(testChartDefinition);
        assertThat(chartScript, containsString("function load_test_chart()"));
        assertThat(chartScript, containsString("type: 'bar',"));
        assertThat(chartScript, containsString("label: '1'"));
        assertThat(chartScript, containsString("data: [1,2]"));
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

    private List<LabeledKeyValueDataItem> getTestDataItems() {
        List<LabeledKeyValueDataItem> dataItems = new ArrayList<>();

        dataItems.add(new LabeledKeyValueDataItem("1", new KeyValueDataItem("One", 1)));
        dataItems.add(new LabeledKeyValueDataItem("1", new KeyValueDataItem("Two", 2)));

        return dataItems;
    }
}