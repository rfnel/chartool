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
        List<KeyValueDataItem> labelValuePairs = jdbcTemplate.query(chartDefinition.getQuery(), (ResultSet resultSet, int i) -> {
            KeyValueDataItem dataItem = new KeyValueDataItem(resultSet.getString(1), resultSet.getDouble(2));

            return dataItem;
        });

        return labelValuePairs;
    }
}
