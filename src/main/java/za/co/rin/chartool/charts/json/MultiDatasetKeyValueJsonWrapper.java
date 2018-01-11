package za.co.rin.chartool.charts.json;

import za.co.rin.chartool.charts.config.ChartDefinition;
import za.co.rin.chartool.charts.datasource.KeyValueDataItem;
import za.co.rin.chartool.charts.datasource.LabeledKeyValueDataItem;
import za.co.rin.chartool.charts.generators.BarChartDatasetTemplateMapper;
import za.co.rin.chartool.charts.templates.ScriptTemplate;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MultiDatasetKeyValueJsonWrapper {

    private ChartDefinition chartDefinition;
    private List<LabeledKeyValueDataItem> dataItems;

    private List<String> labels = new ArrayList<>();
    private Map<String, List<KeyValueDataItem>> datasets = new TreeMap<>();

    public MultiDatasetKeyValueJsonWrapper(ChartDefinition chartDefinition, List<LabeledKeyValueDataItem> dataItems) {
        this.chartDefinition = chartDefinition;
        this.dataItems = dataItems;

        this.init();
    }

    public void init() {
        for (LabeledKeyValueDataItem labeledDataItem : dataItems) {
            String datasetLabel = labeledDataItem.getDatasetLabel();

            List<KeyValueDataItem> dataSetItems = datasets.get(datasetLabel);
            if (dataSetItems == null) {
                dataSetItems = new ArrayList<>();

                datasets.put(datasetLabel, dataSetItems);
            }

            dataSetItems.add(labeledDataItem.getDataItem());

            String label = "'" + labeledDataItem.getDataItem().getKey() + "'";
            if (!labels.contains(label)) {
                labels.add(label);
            }
        }
    }

    public String getDataSets(ScriptTemplate scriptTemplate, BarChartDatasetTemplateMapper datasetTemplateMapper) {
        AtomicInteger counter = new AtomicInteger(0);

        List<String> datasetText = datasets.
                keySet().
                stream().
                map(datasetLabel -> datasetTemplateMapper.mapDatasetToTemplate(scriptTemplate.newInstance(), datasetLabel, datasets.get(datasetLabel), chartDefinition.getIndex() + counter.getAndIncrement())).
                collect(Collectors.toList());

        return String.join(",\n", datasetText);
    }

    public String getLabels() {
        return String.join(",", labels);
    }
}
