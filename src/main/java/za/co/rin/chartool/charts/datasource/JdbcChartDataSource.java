package za.co.rin.chartool.charts.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import za.co.rin.chartool.charts.config.ChartDefinition;

import java.sql.ResultSet;
import java.util.List;

@Component
public class JdbcChartDataSource implements ChartDataSource {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<KeyValueDataItem> getKeyValueDataItems(ChartDefinition chartDefinition) {
        List<KeyValueDataItem> dataItems = jdbcTemplate.query(chartDefinition.getQuery(), (ResultSet resultSet, int i) -> {
            KeyValueDataItem dataItem = new KeyValueDataItem(resultSet.getString(1), resultSet.getDouble(2));

            return dataItem;
        });

        return dataItems;
    }

    @Override
    public List<LabeledKeyValueDataItem> getLabeledKeyValueDataItems(ChartDefinition chartDefinition) {
        List<LabeledKeyValueDataItem> dataItems = jdbcTemplate.query(chartDefinition.getQuery(), (ResultSet resultSet, int i) -> {
            String label = resultSet.getString(1);
            KeyValueDataItem dataItem = new KeyValueDataItem(resultSet.getString(2), resultSet.getDouble(3));

            LabeledKeyValueDataItem labeledDataItem = new LabeledKeyValueDataItem(label, dataItem);

            return labeledDataItem;
        });

        return dataItems;
    }

    @Override
    public List<PointDataItem> getPointDataItems(ChartDefinition chartDefinition) {
        List<PointDataItem> dataItems = jdbcTemplate.query(chartDefinition.getQuery(), (ResultSet resultSet, int i) -> {
            PointDataItem dataItem = new PointDataItem(resultSet.getDouble(1), resultSet.getDouble(2));

            return dataItem;
        });

        return dataItems;
    }
}
