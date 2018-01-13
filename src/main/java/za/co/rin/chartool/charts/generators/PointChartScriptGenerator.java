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
public class PointChartScriptGenerator implements ChartScriptGenerator {

    @Autowired
    private ChartDataSource chartDataSource;
    @Autowired
    private ChartColorManager colorManager;
    @Autowired
    private TemplateManager templateManager;

    public String getChartScript(ChartDefinition chartDefinition) {
        return createChartScript(chartDefinition);
    }

    private String createChartScript(ChartDefinition chartDefinition) {
        ChartSettings chartSettings = ChartSettings.getSettings(chartDefinition);
        ChartData<PointDataItem> chartData = chartDataSource.getPointDatasets(chartDefinition);

        ScriptTemplate template = templateManager.getScriptTemplate(chartSettings.getChartScriptTemplate());

        String datasets = mapDataSets(chartDefinition, chartSettings, chartData);

        return template
                .set("CHART_ID", chartDefinition.getId())
                .set("CHART_NAME", chartDefinition.getName())
                .set("DATASETS", datasets)
                .set("LOAD_FUNCTION", chartDefinition.getLoadFunction())
                .toScriptText();
    }

    private String mapDataSets(ChartDefinition chartDefinition, ChartSettings chartSettings, ChartData<PointDataItem> chartData) {
        List<String> jsonDataSets = new ArrayList<>();

        ScriptTemplate datasetTemplate = templateManager.getScriptTemplate(chartSettings.getChartDatasetTemplate());
        List<Dataset<PointDataItem>> datasets = chartData.getDatasets();
        for (int i = 0; i < datasets.size(); i++) {
            Dataset<PointDataItem> dataset = datasets.get(i);

            String datasetJson = datasetTemplate
                    .newInstance()
                    .set("DATASET_LABEL", dataset.getDatasetLabel())
                    .set("DATA", JsonUtil.pointsToJsonList(dataset.getDataItems()))
                    .set("BACKGROUND_COLOR", colorManager.getChartColorsJson(chartDefinition.getIndex() + i, getChartColorCount(chartSettings, dataset)))
                    .toScriptText();

            jsonDataSets.add(datasetJson);
        }

        return String.join(",", jsonDataSets);
    }

    private int getChartColorCount(ChartSettings chartSettings, Dataset<PointDataItem> dataset) {
        switch (chartSettings.getColorStrategy()) {
            case SINGLE_COLOR:
                return 1;
            case MULTIPLE_COLORS:
                return dataset.size();
            default:
                throw new IllegalArgumentException("Unhandled color strategy: " + chartSettings.getColorStrategy());
        }
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