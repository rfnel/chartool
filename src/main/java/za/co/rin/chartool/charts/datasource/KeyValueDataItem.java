package za.co.rin.chartool.charts.datasource;


public class KeyValueDataItem implements DataItem {

    private final String datasetLabel;
    private final String key;
    private final Number value;

    public KeyValueDataItem(String key, Number value) {
        this.datasetLabel = "Default";
        this.key = key;
        this.value = value;
    }

    @Override
    public String getDataSetLabel() {
        return datasetLabel;
    }

    public String getKey() {
        return key;
    }

    public Number getValue() {
        return value;
    }
}
