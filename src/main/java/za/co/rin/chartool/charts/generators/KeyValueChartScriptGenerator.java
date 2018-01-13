package za.co.rin.chartool.charts.generators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.rin.chartool.charts.colors.ChartColorManager;
import za.co.rin.chartool.charts.config.ChartDefinition;
import za.co.rin.chartool.charts.data.ChartData;
import za.co.rin.chartool.charts.data.ChartDataSource;
import za.co.rin.chartool.charts.data.Dataset;
import za.co.rin.chartool.charts.data.KeyValueDataItem;
import za.co.rin.chartool.charts.json.JsonUtil;
import za.co.rin.chartool.charts.templates.ScriptTemplate;
import za.co.rin.chartool.charts.templates.TemplateManager;

import java.util.ArrayList;
import java.util.List;

@Component
public class KeyValueChartScriptGenerator implements ChartScriptGenerator {

    @Autowired
    private ChartDataSource chartDataSource;
    @Autowired
    private ChartColorManager colorManager;
    @Autowired
    private TemplateManager templateManager;

    @Override
    public String getChartScript(ChartDefinition chartDefinition) {
        return createChartScript(chartDefinition);
    }

    private String createChartScript(ChartDefinition chartDefinition) {
        ChartSettings chartSettings = ChartSettings.getSettings(chartDefinition);
        ChartData<KeyValueDataItem> chartData = chartDataSource.getKeyValueDatasets(chartDefinition);

        ScriptTemplate template = templateManager.getScriptTemplate(chartSettings.getChartScriptTemplate());

        String datasets = mapDataSets(chartDefinition, chartSettings, chartData);
        String labels = JsonUtil.stringsToJsonList(chartData.getLabels());

        return template
                .set("CHART_ID", chartDefinition.getId())
                .set("CHART_NAME", chartDefinition.getName())
                .set("DATASETS", datasets)
                .set("LABELS", labels)
                .set("LOAD_FUNCTION", chartDefinition.getLoadFunction())
                .toScriptText();
    }

    private String mapDataSets(ChartDefinition chartDefinition, ChartSettings chartSettings, ChartData<KeyValueDataItem> chartData) {
        List<String> jsonDataSets = new ArrayList<>();

        ScriptTemplate datasetTemplate = templateManager.getScriptTemplate(chartSettings.getChartDatasetTemplate());
        List<Dataset<KeyValueDataItem>> datasets = chartData.getDatasets();
        for (int i = 0; i < datasets.size(); i++) {
            Dataset<KeyValueDataItem> dataset = datasets.get(i);

            String datasetJson = datasetTemplate
                    .newInstance()
                    .set("DATASET_LABEL", dataset.getDatasetLabel())
                    .set("DATA", JsonUtil.valuesToJsonList(dataset.getDataItems()))
                    .set("BACKGROUND_COLOR", colorManager.getChartColorsJson(chartDefinition.getIndex() + i, getChartColorCount(chartSettings, dataset)))
                    .toScriptText();

            jsonDataSets.add(datasetJson);
        }

        return String.join(",", jsonDataSets);
    }

    private int getChartColorCount(ChartSettings chartSettings, Dataset<KeyValueDataItem> dataset) {
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
