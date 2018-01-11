package za.co.rin.chartool.charts.templates;

import za.co.rin.chartool.charts.datasource.KeyValueDataItem;

import java.util.List;

public interface DatasetTemplateMapper<T extends Object> {

    public String mapDatasetToTemplate(ScriptTemplate template, String datasetLabel, List<KeyValueDataItem> dataItems, int counter);

}
