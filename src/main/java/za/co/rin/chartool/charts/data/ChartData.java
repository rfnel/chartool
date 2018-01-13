package za.co.rin.chartool.charts.data;

import java.util.*;

public class ChartData<T> {

    private List<Dataset<T>> datasets = new ArrayList<>();
    private Set<String> labels = new HashSet<>();

    public ChartData() {
    }

    public void addDataset(Dataset<T> dataset) {
        this.datasets.add(dataset);
    }

    public void addLabel(String label) {
        this.labels.add(label);
    }

    public List<String> getLabels() {
        return Collections.unmodifiableList(new ArrayList<>(labels));
    }

    public List<Dataset<T>> getDatasets() {
        return Collections.unmodifiableList(datasets);
    }
}