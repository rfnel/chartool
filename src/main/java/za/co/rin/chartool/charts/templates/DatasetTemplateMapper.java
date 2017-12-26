package za.co.rin.chartool.charts.templates;

import za.co.rin.chartool.charts.datasource.DataItem;

import java.util.List;

public interface DatasetTemplateMapper<T extends DataItem> {

    public String mapDatasetToTemplate(ScriptTemplate template, String datasetLabel, List<T> dataItems);

}
