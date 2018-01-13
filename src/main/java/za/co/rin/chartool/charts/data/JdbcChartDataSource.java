package za.co.rin.chartool.charts.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import za.co.rin.chartool.charts.config.ChartDefinition;

import java.security.Key;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JdbcChartDataSource implements ChartDataSource {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<PointDataItem> getPointDataItems(ChartDefinition chartDefinition) {
        List<PointDataItem> dataItems = jdbcTemplate.query(chartDefinition.getQuery(), (ResultSet resultSet, int i) -> {
            PointDataItem dataItem = new PointDataItem(resultSet.getDouble(1), resultSet.getDouble(2));

            return dataItem;
        });

        return dataItems;
    }

    @Override
    public ChartData getKeyValueDatasets(ChartDefinition chartDefinition) {
        return jdbcTemplate.query(chartDefinition.getQuery(), new KeyValueResultSetExtractor());
    }

    private static class KeyValueResultSetExtractor implements ResultSetExtractor<ChartData> {

        @Override
        public ChartData extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            ChartData<KeyValueDataItem> chartData = new ChartData<>();

            Map<String, Dataset<KeyValueDataItem>> datasetMap = new HashMap<>();

            //TODO:  Consider moving this logic into ChartData class.
            while (resultSet.next()) {
                String datasetLabel = resultSet.getString(1);
                String dataItemLabel = resultSet.getString(2);
                double dataItemValue = resultSet.getDouble(3);

                KeyValueDataItem dataItem = new KeyValueDataItem(dataItemLabel, dataItemValue);

                Dataset<KeyValueDataItem> dataset = datasetMap.get(datasetLabel);
                if (dataset == null) {
                    dataset = new Dataset<>(datasetLabel);

                    datasetMap.put(datasetLabel, dataset);
                    chartData.addDataset(dataset);
                }

                dataset.addDataItem(dataItem);
                chartData.addLabel(dataItemLabel);
            }

            return chartData;
        }
    }
}
