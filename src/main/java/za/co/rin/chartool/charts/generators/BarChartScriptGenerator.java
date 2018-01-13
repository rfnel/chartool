package za.co.rin.chartool.charts.generators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.rin.chartool.charts.colors.ChartColorManager;
import za.co.rin.chartool.charts.config.ChartDefinition;
import za.co.rin.chartool.charts.data.*;
import za.co.rin.chartool.charts.json.JsonUtil;
import za.co.rin.chartool.charts.templates.ScriptTemplate;
import za.co.rin.chartool.charts.templates.TemplateManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BarChartScriptGenerator implements ChartScriptGenerator {

    @Autowired
    private ChartDataSource chartDataSource;
    @Autowired
    private TemplateManager templateManager;
    @Autowired
    private ChartColorManager colorManager;

    private static final String CHART_SCRIPT_TEMPLATE = "js_templates/multi_dataset_bar_chart.template";
    private static final String CHART_DATASET_TEMPLATE = "js_templates/dataset_templates/bar_chart_dataset.template";

    public String getChartScript(ChartDefinition chartDefinition) {
        return createChartScript(chartDefinition);
    }

    private String createChartScript(ChartDefinition chartDefinition) {
        ChartData<KeyValueDataItem> chartData = chartDataSource.getKeyValueDatasets(chartDefinition);

        ScriptTemplate template = templateManager.getScriptTemplate(CHART_SCRIPT_TEMPLATE);

        String datasets = mapDataSets(chartDefinition, chartData);
        String labels = JsonUtil.stringsToJsonList(chartData.getLabels());

        return template
                .set("CHART_ID", chartDefinition.getId())
                .set("CHART_NAME", chartDefinition.getName())
                .set("DATASETS", datasets)
                .set("LABELS", labels)
                .set("LOAD_FUNCTION", chartDefinition.getLoadFunction())
                .toScriptText();
    }

    private String mapDataSets(ChartDefinition chartDefinition, ChartData<KeyValueDataItem> chartData) {
        List<String> jsonDataSets = new ArrayList<>();

        ScriptTemplate datasetTemplate = templateManager.getScriptTemplate(CHART_DATASET_TEMPLATE);
        List<Dataset<KeyValueDataItem>> datasets = chartData.getDatasets();
        for (int i = 0; i < datasets.size(); i++) {
            List<Number> values = datasets.get(i).getDataItems().stream().map(dataItem -> dataItem.getValue()).collect(Collectors.toList());

            String datasetJson = datasetTemplate
                    .newInstance()
                    .set("DATASET_LABEL", datasets.get(i).getDatasetLabel())
                    .set("DATA", JsonUtil.valuesToJsonList(values))
                    .set("BACKGROUND_COLOR", colorManager.getChartColorsJson(chartDefinition.getIndex() + i, 1))
                    .toScriptText();

            jsonDataSets.add(datasetJson);
        }

        return String.join(",", jsonDataSets);
    }

    protected void setChartDataSource(ChartDataSource chartDataSource) {
        this.chartDataSource = chartDataSource;
    }

    protected void setTemplateManager(TemplateManager templateManager) {
        this.templateManager = templateManager;
    }

    protected void setColorManager(ChartColorManager colorManager) {
        this.colorManager = colorManager;
    }
}