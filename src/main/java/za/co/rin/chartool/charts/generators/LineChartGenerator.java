package za.co.rin.chartool.charts.generators;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import za.co.rin.chartool.charts.config.ChartDefinition;

import java.sql.ResultSet;
import java.util.List;

@Component
public class LineChartGenerator implements ChartGenerator {

    //TODO:  Extract database logic into separate class.
    //TODO:  Unit testing.

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public JFreeChart generateChart(ChartDefinition chartDefinition) {
        List<LabelValuePair> labelValuePairs = retrieveData(chartDefinition);
        DefaultCategoryDataset categoryDataSet = createCategoryDataSet(labelValuePairs);

        return ChartFactory.createLineChart(chartDefinition.getName(),
                "X",
                "Y",
                categoryDataSet,
                PlotOrientation.VERTICAL,
                true,
                false,
                false);
    }

    private DefaultCategoryDataset createCategoryDataSet(List<LabelValuePair> labelValuePairs) {
        DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
        for (LabelValuePair labelValuePair : labelValuePairs) {
            categoryDataset.setValue(labelValuePair.value, "X", labelValuePair.label);
        }

        return categoryDataset;
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
