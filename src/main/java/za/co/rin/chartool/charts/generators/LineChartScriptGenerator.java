package za.co.rin.chartool.charts.generators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.rin.chartool.charts.config.ChartDefinition;
import za.co.rin.chartool.charts.datasource.ChartDataSource;
import za.co.rin.chartool.charts.datasource.KeyValueDataItem;
import za.co.rin.chartool.charts.json.KeyValueJsonWrapper;

import java.util.List;

@Component
public class LineChartScriptGenerator implements ChartScriptGenerator {

    @Autowired
    private ChartDataSource chartDataSource;

    @Autowired
    private ChartColorManager chartColorManager;

    private static final String CHART_SCRIPT_TEMPLATE = "js_templates/line_chart.template";

    public String getChartScript(ChartDefinition chartDefinition) {
        return createChartScript(chartDefinition);
    }

    private String createChartScript(ChartDefinition chartDefinition) {
        List<KeyValueDataItem> dataItems = chartDataSource.getKeyValueDataItems(chartDefinition);
        KeyValueJsonWrapper jsonWrapper = new KeyValueJsonWrapper(dataItems);

        String colors = chartColorManager.getChartColorsJson(1);

        //TODO:  Encapsulate template logic in template class.
        String template = TemplateReader.getTemplate(CHART_SCRIPT_TEMPLATE);

        return template
                .replaceAll("\\$CHART_ID", chartDefinition.getId())
                .replaceAll("\\$CHART_NAME", chartDefinition.getName())
                .replaceAll("\\$DATASET_LABEL", chartDefinition.getLabel())
                .replaceAll("\\$BACKGROUND_COLOR", colors)
                .replaceAll("\\$DATA", jsonWrapper.getData())
                .replaceAll("\\$LABELS", jsonWrapper.getLabels())
                .replaceAll("\\$LOAD_FUNCTION", chartDefinition.getLoadFunction());
    }

    protected void setChartColorManager(ChartColorManager chartColorManager) {
        this.chartColorManager = chartColorManager;
    }

    protected void setChartDataSource(ChartDataSource chartDataSource) {
        this.chartDataSource = chartDataSource;
    }
}