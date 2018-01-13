package za.co.rin.chartool.charts.data;

public class KeyValueDataItem {

    private final String key;
    private final Number value;

    public KeyValueDataItem(String key, Number value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Number getValue() {
        return value;
    }
}
