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

@Component
public class ScatterChartScriptGenerator implements ChartScriptGenerator {

    @Autowired
    private ChartDataSource chartDataSource;
    @Autowired
    private ChartColorManager colorManager;
    @Autowired
    private TemplateManager templateManager;

    private static final String CHART_SCRIPT_TEMPLATE = "js_templates/scatter_charts/scatter_chart.template";
    private static final String CHART_DATASET_TEMPLATE = "js_templates/scatter_charts/scatter_chart_dataset.template";

    public String getChartScript(ChartDefinition chartDefinition) {
        return createChartScript(chartDefinition);
    }

    private String createChartScript(ChartDefinition chartDefinition) {
        ChartData<PointDataItem> chartData = chartDataSource.getPointDatasets(chartDefinition);

        ScriptTemplate template = templateManager.getScriptTemplate(CHART_SCRIPT_TEMPLATE);

        String datasets = mapDataSets(chartDefinition, chartData);

        return template
                .set("CHART_ID", chartDefinition.getId())
                .set("CHART_NAME", chartDefinition.getName())
                .set("DATASETS", datasets)
                .set("LOAD_FUNCTION", chartDefinition.getLoadFunction())
                .toScriptText();
    }

    private String mapDataSets(ChartDefinition chartDefinition, ChartData<PointDataItem> chartData) {
        List<String> jsonDataSets = new ArrayList<>();

        ScriptTemplate datasetTemplate = templateManager.getScriptTemplate(CHART_DATASET_TEMPLATE);
        List<Dataset<PointDataItem>> datasets = chartData.getDatasets();
        for (int i = 0; i < datasets.size(); i++) {
            Dataset<PointDataItem> dataset = datasets.get(i);

            String datasetJson = datasetTemplate
                    .newInstance()
                    .set("DATASET_LABEL", dataset.getDatasetLabel())
                    .set("DATA", JsonUtil.pointsToJsonList(dataset.getDataItems()))
                    .set("BACKGROUND_COLOR", colorManager.getChartColorsJson(chartDefinition.getIndex() + i, 1))
                    .toScriptText();

            jsonDataSets.add(datasetJson);
        }

        return String.join(",", jsonDataSets);
    }

    protected void setColorManager(ChartColorManager colorManager) {
        this.colorManager = colorManager;
    }

    protected void setChartDataSource(ChartDataSource chartDataSource) {
        this.chartDataSource = chartDataSource;
    }

    protected void setTemplateManager(TemplateManager templateManager) {
        this.templateManager = templateManager;
    }
}