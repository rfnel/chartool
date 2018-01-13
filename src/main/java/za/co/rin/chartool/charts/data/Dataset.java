package za.co.rin.chartool.charts.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dataset<T> {

    private String datasetLabel;
    private List<T> dataItems = new ArrayList<>();

    public Dataset(String datasetLabel) {
        this.datasetLabel = datasetLabel;
    }

    public void addDataItem(T dataItem) {
        this.dataItems.add(dataItem);
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
