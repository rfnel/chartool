package za.co.rin.chartool.charts.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import za.co.rin.chartool.charts.config.ChartDefinition;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JdbcChartDataSource implements ChartDataSource {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ChartData<KeyValueDataItem> getKeyValueDatasets(ChartDefinition chartDefinition) {
        return jdbcTemplate.query(chartDefinition.getQuery(), new KeyValueResultSetExtractor());
    }

    @Override
    public ChartData<PointDataItem> getPointDatasets(ChartDefinition chartDefinition) {
        return jdbcTemplate.query(chartDefinition.getQuery(), new PointResultSetExtractor());
    }

    private static class KeyValueResultSetExtractor implements ResultSetExtractor<ChartData<KeyValueDataItem>> {

        @Override
        public ChartData<KeyValueDataItem> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
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

    private static class PointResultSetExtractor implements ResultSetExtractor<ChartData<PointDataItem>> {

        @Override
        public ChartData<PointDataItem> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            ChartData<PointDataItem> chartData = new ChartData<>();

            Map<String, Dataset<PointDataItem>> datasetMap = new HashMap<>();

            //TODO:  Consider moving this logic into ChartData class.
            while (resultSet.next()) {
                String datasetLabel = resultSet.getString(1);
                double dataItemX = resultSet.getDouble(2);
                double dataItemY = resultSet.getDouble(3);

                PointDataItem dataItem = new PointDataItem(dataItemX, dataItemY);

                Dataset<PointDataItem> dataset = datasetMap.get(datasetLabel);
                if (dataset == null) {
                    dataset = new Dataset<>(datasetLabel);

                    datasetMap.put(datasetLabel, dataset);
                    chartData.addDataset(dataset);
                }

                dataset.addDataItem(dataItem);
            }

            return chartData;
        }
    }
}
