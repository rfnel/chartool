package za.co.rin.chartool.charts.datasource;

import za.co.rin.chartool.charts.config.ChartDefinition;

import java.util.List;

public interface ChartDataSource {

    List<KeyValueDataItem> getKeyValueDataItems(ChartDefinition chartDefinition);
    List<LabeledKeyValueDataItem> getLabeledKeyValueDataItems(ChartDefinition chartDefinition);
    List<PointDataItem> getPointDataItems(ChartDefinition chartDefinition);
}
