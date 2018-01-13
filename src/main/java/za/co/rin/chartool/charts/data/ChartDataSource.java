package za.co.rin.chartool.charts.data;

import za.co.rin.chartool.charts.config.ChartDefinition;

public interface ChartDataSource {

    ChartData<KeyValueDataItem> getKeyValueDatasets(ChartDefinition chartDefinition);
    ChartData<PointDataItem> getPointDatasets(ChartDefinition chartDefinition);

}
