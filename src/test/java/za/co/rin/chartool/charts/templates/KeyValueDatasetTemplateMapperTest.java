package za.co.rin.chartool.charts.templates;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import za.co.rin.chartool.charts.colors.ChartColorManager;
import za.co.rin.chartool.charts.datasource.KeyValueDataItem;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class KeyValueDatasetTemplateMapperTest {

    private KeyValueDatasetTemplateMapper templateMapper;

    private JUnit4Mockery context = new JUnit4Mockery();
    private ChartColorManager chartColorManagerMock;

    private static final String TEST_COLORS = "'rgba(0, 0, 139, 0.5)', 'rgba(54, 162, 235, 0.5)'";
    private static final String TEMPLATE_TEXT = "{\n" +
            "    data: [$DATA],\n" +
            "    backgroundColor: [$BACKGROUND_COLORS],\n" +
            "}";

    @Before
    public void setUp() {
        this.chartColorManagerMock = context.mock(ChartColorManager.class);

        this.templateMapper = new KeyValueDatasetTemplateMapper(this.chartColorManagerMock);
    }

    @Test
    public void testMapDatasetToTemplate() throws Exception {
        context.checking(new Expectations() {{
            oneOf(chartColorManagerMock).getChartColorsJson(2);
            will(returnValue(TEST_COLORS));
        }});

        String expectedResult = "{\n" +
                "    data: [1,2],\n" +
                "    backgroundColor: ['rgba(0, 0, 139, 0.5)', 'rgba(54, 162, 235, 0.5)'],\n" +
                "}";

        List<KeyValueDataItem> dataItems = getTestDataItems();

        String mappedDataset = templateMapper.mapDatasetToTemplate(new ScriptTemplate(TEMPLATE_TEXT), "test", dataItems);

        assertThat(mappedDataset, is(equalTo(expectedResult)));
    }

    private List<KeyValueDataItem> getTestDataItems() {
        List<KeyValueDataItem> dataItems = new ArrayList<>();
        dataItems.add(new KeyValueDataItem("One", 1));
        dataItems.add(new KeyValueDataItem("Two", 2));

        return dataItems;
    }

    @After
    public void tearDown() {
        context.assertIsSatisfied();
    }
}