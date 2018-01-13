package za.co.rin.chartool.charts.data;

import za.co.rin.chartool.charts.config.ChartDefinition;

import java.util.List;

public interface ChartDataSource {

    List<KeyValueDataItem> getKeyValueDataItems(ChartDefinition chartDefinition);
    List<PointDataItem> getPointDataItems(ChartDefinition chartDefinition);

    ChartData<KeyValueDataItem> getKeyValueDatasets(ChartDefinition chartDefinition);
}
