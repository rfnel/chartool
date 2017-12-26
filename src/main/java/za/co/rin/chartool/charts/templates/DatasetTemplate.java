package za.co.rin.chartool.charts.templates;

import za.co.rin.chartool.charts.datasource.DataItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class DatasetTemplate<T extends DataItem> {

    private String templateText;
    private String text;

    public DatasetTemplate(String templateText) {
        this.templateText = templateText;
    }

    public String toDatasetText() {
        return this.text;
    }

    public void addDatasets(List<T> dataItems, DatasetTemplateMapper<T> templateMapper) {
        Map<String, List<T>> datasets = mapItemsToDatasets(dataItems);

        List<String> datasetText = datasets.
                keySet().
                stream().
                map(datasetLabel -> templateMapper.mapDatasetToTemplate(new ScriptTemplate(templateText), datasetLabel, datasets.get(datasetLabel)))
                .collect(Collectors.toList());

        this.text = String.join(",\n", datasetText);
    }

    private Map<String, List<T>> mapItemsToDatasets(List<T> dataItems) {
        Map<String, List<T>> datasets = new TreeMap<>();

        for (T dataItem : dataItems) {
            String datasetLabel = dataItem.getDataSetLabel();

            List<T> dataSetItems = datasets.get(datasetLabel);
            if (dataSetItems == null) {
                dataSetItems = new ArrayList<>();

                datasets.put(datasetLabel, dataSetItems);
            }

            dataSetItems.add(dataItem);
        }

        return datasets;
    }
}
