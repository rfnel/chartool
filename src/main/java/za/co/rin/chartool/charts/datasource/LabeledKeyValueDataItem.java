package za.co.rin.chartool.charts.datasource;

public class LabeledKeyValueDataItem extends LabeledDataItem {

    private final KeyValueDataItem dataItem;

    public LabeledKeyValueDataItem(String datasetLabel, KeyValueDataItem dataItem) {
        super(datasetLabel);
        this.dataItem = dataItem;
    }

    public KeyValueDataItem getDataItem() {
        return dataItem;
    }
}
