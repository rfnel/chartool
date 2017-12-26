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
public class PieChartScriptGenerator implements ChartScriptGenerator {

    @Autowired
    private ChartDataSource chartDataSource;
    @Autowired
    private ChartColorManager chartColorManager;
    @Autowired
    private TemplateManager templateManager;

    public static final int PIE_CHART_COLOR_OFFSET = 0;

    private static final String CHART_SCRIPT_TEMPLATE = "js_templates/pie_chart.template";

    public String getChartScript(ChartDefinition chartDefinition) {
        return createChartScript(chartDefinition);
    }

    private String createChartScript(ChartDefinition chartDefinition) {
        List<KeyValueDataItem> dataItems = chartDataSource.getKeyValueDataItems(chartDefinition);
        KeyValueJsonWrapper jsonWrapper = new KeyValueJsonWrapper(dataItems);

        String colors = chartColorManager.getChartColorsJson(PIE_CHART_COLOR_OFFSET, dataItems.size());

        ScriptTemplate template = templateManager.getScriptTemplate(CHART_SCRIPT_TEMPLATE);

        return template
                .set("CHART_ID", chartDefinition.getId())
                .set("CHART_NAME", chartDefinition.getName())
                .set("BACKGROUND_COLORS", colors)
                .set("DATA", jsonWrapper.getData())
                .set("LABELS", jsonWrapper.getLabels())
                .set("LOAD_FUNCTION", chartDefinition.getLoadFunction())
                .toScriptText();
    }

    protected void setChartDataSource(ChartDataSource chartDataSource) {
        this.chartDataSource = chartDataSource;
    }

    protected void setChartColorManager(ChartColorManager chartColorManager) {
        this.chartColorManager = chartColorManager;
    }

    protected void setTemplateManager(TemplateManager templateManager) {
        this.templateManager = templateManager;
    }
}