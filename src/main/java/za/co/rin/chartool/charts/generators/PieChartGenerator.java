package za.co.rin.chartool.charts.generators;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import za.co.rin.chartool.charts.config.ChartDefinition;

import java.sql.ResultSet;
import java.util.List;

@Component
public class PieChartGenerator implements ChartGenerator {

    //TODO:  Extract database logic into seperate class.
    //TODO:  Unit testing.

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public JFreeChart generateChart(ChartDefinition chartDefinition) {
        List<LabelValuePair> labelValuePairs = retrieveData(chartDefinition);
        PieDataset pieDataSet = createPieDataSet(labelValuePairs);

        return ChartFactory.createPieChart3D(chartDefinition.getName(),
                pieDataSet,
                false,
                false,
                false);
    }

    private PieDataset createPieDataSet(List<LabelValuePair> labelValuePairs) {
        DefaultPieDataset pieDataSet = new DefaultPieDataset();
        for (LabelValuePair labelValuePair : labelValuePairs) {
            pieDataSet.setValue(labelValuePair.label, labelValuePair.value);
        }

        return pieDataSet;
    }

    private List<LabelValuePair> retrieveData(ChartDefinition chartDefinition) {
        List<LabelValuePair> labelValuePairs = jdbcTemplate.query(chartDefinition.getQuery(), (ResultSet resultSet, int i) -> {
            LabelValuePair labelValuePair = new LabelValuePair();
            labelValuePair.label = resultSet.getString(1);
            labelValuePair.value = resultSet.getDouble(2);

            return labelValuePair;
        });

        return labelValuePairs;
    }

    private static class LabelValuePair {
        String label;
        double value;
    }
}
