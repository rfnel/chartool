package za.co.rin.chartool.charts.datasource;

public abstract class LabeledDataItem {

    private final String datasetLabel;

    public LabeledDataItem(String datasetLabel) {
        this.datasetLabel = datasetLabel;
    }

    public String getDatasetLabel() {
        return datasetLabel;
    }
}
