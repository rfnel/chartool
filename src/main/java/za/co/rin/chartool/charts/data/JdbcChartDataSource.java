package za.co.rin.chartool.charts.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import za.co.rin.chartool.charts.config.ChartDefinition;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

@Component
public class JdbcChartDataSource implements ChartDataSource {

//    @Autowired
//    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("datasources")
    private Map<String, JdbcTemplate> jdbcTemplates;


    @Override
    public ChartData<KeyValueDataItem> getKeyValueDatasets(ChartDefinition chartDefinition) {
        System.out.println("Datasource: " + chartDefinition.getDatasource());
        System.out.println("Datasources size : " + jdbcTemplates.size());
        return jdbcTemplates.get(chartDefinition.getDatasource()).query(chartDefinition.getQuery(), new KeyValueResultSetExtractor());
    }

    @Override
    public ChartData<PointDataItem> getPointDatasets(ChartDefinition chartDefinition) {
        return jdbcTemplates.get(chartDefinition.getDatasource()).query(chartDefinition.getQuery(), new PointResultSetExtractor());
    }

    private static class KeyValueResultSetExtractor implements ResultSetExtractor<ChartData<KeyValueDataItem>> {

        @Override
        public ChartData<KeyValueDataItem> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            ChartData<KeyValueDataItem> chartData = new ChartData<>();

            Map<String, List<KeyValueDataItem>> dataMap = new HashMap<>();

            while (resultSet.next()) {
                String datasetLabel = resultSet.getString(1);
                String dataItemLabel = resultSet.getString(2);
                double dataItemValue = resultSet.getDouble(3);

                KeyValueDataItem dataItem = new KeyValueDataItem(dataItemLabel, dataItemValue);

                List<KeyValueDataItem> dataItemList = dataMap.get(datasetLabel);
                if (dataItemList == null) {
                    dataItemList = new ArrayList<>();

                    dataMap.put(datasetLabel, dataItemList);
                }

                dataItemList.add(dataItem);
                chartData.addLabel(dataItemLabel);
            }

            for (String dataSetLabel : dataMap.keySet()) {
                List<KeyValueDataItem> standardizedDataset = standardizeDataset(chartData.getLabels(), dataMap.get(dataSetLabel));

                chartData.addDataset(new Dataset<>(dataSetLabel, standardizedDataset));
            }

            return chartData;
        }

        private List<KeyValueDataItem> standardizeDataset(List<String> labels, List<KeyValueDataItem> dataItems) {
            KeyValueDataItem[] standardizedDataItems = new KeyValueDataItem[labels.size()];

            for (KeyValueDataItem dataItem : dataItems) {
                standardizedDataItems[labels.indexOf(dataItem.getKey())] = dataItem;
            }

            return Arrays.asList(standardizedDataItems);
        }

        //TODO:  Standardize data here.
        //First, get all labels.
        //Then, for each data set, add each item at index corresponding to label.
    }

    private static class PointResultSetExtractor implements ResultSetExtractor<ChartData<PointDataItem>> {

        @Override
        public ChartData<PointDataItem> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            ChartData<PointDataItem> chartData = new ChartData<>();

            Map<String, List<PointDataItem>> dataMap = new HashMap<>();

            while (resultSet.next()) {
                String datasetLabel = resultSet.getString(1);
                double dataItemX = resultSet.getDouble(2);
                double dataItemY = resultSet.getDouble(3);

                PointDataItem dataItem = new PointDataItem(dataItemX, dataItemY);

                List<PointDataItem> dataItemList = dataMap.get(datasetLabel);
                if (dataItemList == null) {
                    dataItemList = new ArrayList<>();

                    dataMap.put(datasetLabel, dataItemList);
                }
            }

            return chartData;
        }
    }
}
