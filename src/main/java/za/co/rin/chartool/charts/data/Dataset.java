package za.co.rin.chartool.charts.data;

import java.util.Collections;
import java.util.List;

public class Dataset<T> {

    private final String datasetLabel;
    private final List<T> dataItems;

    public Dataset(String datasetLabel, List<T> dataItems) {
        this.datasetLabel = datasetLabel;
        this.dataItems = dataItems;
    }

    public String getDatasetLabel() {
        return datasetLabel;
    }

    public List<T> getDataItems() {
        return Collections.unmodifiableList(dataItems);
    }

    public int size() {
        return dataItems.size();
    }
}
