package za.co.rin.chartool.charts.templates;

import za.co.rin.chartool.charts.colors.ChartColorManager;
import za.co.rin.chartool.charts.datasource.KeyValueDataItem;
import za.co.rin.chartool.charts.json.KeyValueJsonWrapper;

import java.util.List;

public class KeyValueDatasetTemplateMapper implements DatasetTemplateMapper<KeyValueDataItem> {

    private final ChartColorManager colorManager;

    public KeyValueDatasetTemplateMapper(ChartColorManager colorManager) {
        this.colorManager = colorManager;
    }

    @Override
    public String mapDatasetToTemplate(ScriptTemplate template,  String datasetLabel, List<KeyValueDataItem> dataItems) {
        KeyValueJsonWrapper jsonWrapper = new KeyValueJsonWrapper(dataItems);

        String data = jsonWrapper.getData();

        template.set("DATASET_LABEL", datasetLabel);
        template.set("DATA", data);
        template.set("BACKGROUND_COLORS", colorManager.getChartColorsJson(dataItems.size()));

        return template.toScriptText();
    }
}
