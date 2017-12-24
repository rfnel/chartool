package za.co.rin.chartool.charts.json;

import za.co.rin.chartool.charts.datasource.KeyValueDataItem;

import java.util.List;

public class KeyValueJsonWrapper {

    private String labels;
    private String data;

    public KeyValueJsonWrapper(List<KeyValueDataItem> dataItems) {
        initFields(dataItems);
    }

    private void initFields(List<KeyValueDataItem> dataItems) {
        StringBuilder data = new StringBuilder();
        StringBuilder labels = new StringBuilder();

        for (int i = 0; i < dataItems.size(); i++) {
            labels.append("'" + dataItems.get(i).getKey() + "'");
            data.append(dataItems.get(i).getValue());

            if (i < dataItems.size() - 1) {
                labels.append(",");
                data.append(",");
            }
        }

        this.labels = labels.toString();
        this.data = data.toString();
    }

    public String getLabels() {
        return labels;
    }

    public String getData() {
        return data;
    }
}
