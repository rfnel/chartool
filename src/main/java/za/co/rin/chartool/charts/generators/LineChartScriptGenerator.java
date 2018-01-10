package za.co.rin.chartool.charts.generators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.rin.chartool.charts.colors.ChartColorManager;
import za.co.rin.chartool.charts.config.ChartDefinition;
import za.co.rin.chartool.charts.datasource.ChartDataSource;
import za.co.rin.chartool.charts.datasource.KeyValueDataItem;
import za.co.rin.chartool.charts.json.KeyValueJsonWrapper;
import za.co.rin.chartool.charts.templates.KeyValueDatasetTemplateMapper;
import za.co.rin.chartool.charts.templates.ScriptTemplate;
import za.co.rin.chartool.charts.templates.TemplateManager;

import java.util.List;

@Component
public class LineChartScriptGenerator implements ChartScriptGenerator {

    @Autowired
    private ChartDataSource chartDataSource;

    @Autowired
    private ChartColorManager chartColorManager;
    @Autowired
    private TemplateManager templateManager;

    private static final String CHART_SCRIPT_TEMPLATE = "js_templates/line_chart.template";

    public String getChartScript(ChartDefinition chartDefinition) {
        return createChartScript(chartDefinition);
    }

    private String createChartScript(ChartDefinition chartDefinition) {
        List<KeyValueDataItem> dataItems = chartDataSource.getKeyValueDataItems(chartDefinition);
        KeyValueJsonWrapper jsonWrapper = new KeyValueJsonWrapper(dataItems);

        String colors = chartColorManager.getChartColorsJson(chartDefinition.getIndex(), 1);

        ScriptTemplate scriptTemplate = templateManager.getScriptTemplate(CHART_SCRIPT_TEMPLATE);

        //TODO:  Get data -> map datasets -> add datasets to report.

        return scriptTemplate
                .set("CHART_ID", chartDefinition.getId())
                .set("CHART_NAME", chartDefinition.getName())
                .set("BACKGROUND_COLOR", colors)
                .set("DATASET_LABEL", chartDefinition.getLabel())
                .set("DATA", jsonWrapper.getData())
                .set("LABELS", jsonWrapper.getLabels())
                .set("LOAD_FUNCTION", chartDefinition.getLoadFunction())
                .toScriptText();
    }

    protected void setChartColorManager(ChartColorManager chartColorManager) {
        this.chartColorManager = chartColorManager;
    }

    protected void setChartDataSource(ChartDataSource chartDataSource) {
        this.chartDataSource = chartDataSource;
    }

    protected void setTemplateManager(TemplateManager templateManager) {
        this.templateManager = templateManager;
    }
}