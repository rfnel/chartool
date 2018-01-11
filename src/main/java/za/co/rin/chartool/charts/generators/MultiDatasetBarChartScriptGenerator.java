package za.co.rin.chartool.charts.generators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.rin.chartool.charts.config.ChartDefinition;
import za.co.rin.chartool.charts.datasource.ChartDataSource;
import za.co.rin.chartool.charts.datasource.LabeledKeyValueDataItem;
import za.co.rin.chartool.charts.json.MultiDatasetKeyValueJsonWrapper;
import za.co.rin.chartool.charts.templates.ScriptTemplate;
import za.co.rin.chartool.charts.templates.TemplateManager;

import java.util.List;

@Component
public class MultiDatasetBarChartScriptGenerator implements ChartScriptGenerator {

    @Autowired
    private ChartDataSource chartDataSource;
    @Autowired
    private TemplateManager templateManager;
    @Autowired
    private BarChartDatasetTemplateMapper barChartDatasetTemplateMapper;

    private static final String CHART_SCRIPT_TEMPLATE = "js_templates/multi_dataset_bar_chart.template";
    private static final String CHART_DATASET_TEMPLATE = "js_templates/dataset_templates/bar_chart_dataset.template";

    public String getChartScript(ChartDefinition chartDefinition) {
        return createChartScript(chartDefinition);
    }

    private String createChartScript(ChartDefinition chartDefinition) {
        List<LabeledKeyValueDataItem> dataItems = chartDataSource.getLabeledKeyValueDataItems(chartDefinition);

        ScriptTemplate template = templateManager.getScriptTemplate(CHART_SCRIPT_TEMPLATE);
        ScriptTemplate datasetTemplate = templateManager.getScriptTemplate(CHART_DATASET_TEMPLATE);

        MultiDatasetKeyValueJsonWrapper chartDataJsonMapper = new MultiDatasetKeyValueJsonWrapper(chartDefinition, dataItems);

        String datasets = chartDataJsonMapper.getDataSets(datasetTemplate, barChartDatasetTemplateMapper);
        String labels = chartDataJsonMapper.getLabels();

        return template
                .set("CHART_ID", chartDefinition.getId())
                .set("CHART_NAME", chartDefinition.getName())
                .set("DATASETS", datasets)
                .set("LABELS", labels)
                .set("LOAD_FUNCTION", chartDefinition.getLoadFunction())
                .toScriptText();
    }

    protected void setChartDataSource(ChartDataSource chartDataSource) {
        this.chartDataSource = chartDataSource;
    }

    protected void setTemplateManager(TemplateManager templateManager) {
        this.templateManager = templateManager;
    }

    protected void setBarChartDatasetTemplateMapper(BarChartDatasetTemplateMapper barChartDatasetTemplateMapper) {
        this.barChartDatasetTemplateMapper = barChartDatasetTemplateMapper;
    }
}