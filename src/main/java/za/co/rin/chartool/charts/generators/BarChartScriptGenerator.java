package za.co.rin.chartool.charts.generators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.rin.chartool.charts.colors.ChartColorManager;
import za.co.rin.chartool.charts.config.ChartDefinition;
import za.co.rin.chartool.charts.datasource.ChartDataSource;
import za.co.rin.chartool.charts.datasource.KeyValueDataItem;
import za.co.rin.chartool.charts.json.KeyValueJsonWrapper;
import za.co.rin.chartool.charts.templates.ScriptTemplate;
import za.co.rin.chartool.charts.templates.TemplateManager;

import java.util.List;

@Component
public class BarChartScriptGenerator implements ChartScriptGenerator {

    @Autowired
    private ChartDataSource chartDataSource;
    @Autowired
    private ChartColorManager chartColorManager;
    @Autowired
    private TemplateManager templateManager;

    private static final String CHART_SCRIPT_TEMPLATE = "js_templates/bar_chart.template";

    public String getChartScript(ChartDefinition chartDefinition) {
        return createChartScript(chartDefinition);
    }

    private String createChartScript(ChartDefinition chartDefinition) {
        List<KeyValueDataItem> dataItems = chartDataSource.getKeyValueDataItems(chartDefinition);
        KeyValueJsonWrapper jsonWrapper = new KeyValueJsonWrapper(dataItems);

        String color = chartColorManager.getChartColorsJson(chartDefinition.getIndex(), 1);

        ScriptTemplate template = templateManager.getScriptTemplate(CHART_SCRIPT_TEMPLATE);

        return template
                .set("CHART_ID", chartDefinition.getId())
                .set("CHART_NAME", chartDefinition.getName())
                .set("DATASET_LABEL", chartDefinition.getLabel())
                .set("BACKGROUND_COLOR", color)
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