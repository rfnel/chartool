package za.co.rin.chartool.charts.generators;

import org.springframework.stereotype.Component;
import za.co.rin.chartool.charts.colors.ChartColorManager;
import za.co.rin.chartool.charts.datasource.KeyValueDataItem;
import za.co.rin.chartool.charts.json.KeyValueJsonWrapper;
import za.co.rin.chartool.charts.templates.DatasetTemplateMapper;
import za.co.rin.chartool.charts.templates.ScriptTemplate;

import java.util.List;

@Component
public class BarChartDatasetTemplateMapper implements DatasetTemplateMapper<KeyValueDataItem> {

    private final ChartColorManager colorManager;

    public BarChartDatasetTemplateMapper(ChartColorManager colorManager) {
        this.colorManager = colorManager;
    }

    @Override
    public String mapDatasetToTemplate(ScriptTemplate template, String datasetLabel, List<KeyValueDataItem> dataItems, int colorOffset) {
        KeyValueJsonWrapper jsonWrapper = new KeyValueJsonWrapper(dataItems);

        String data = jsonWrapper.getData();

        template.set("DATASET_LABEL", datasetLabel);
        template.set("DATA", data);
        template.set("BACKGROUND_COLOR", colorManager.getChartColorsJson(colorOffset, 1));

        return template.toScriptText();
    }
}
