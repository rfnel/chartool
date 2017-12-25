package za.co.rin.chartool.charts.generators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.rin.chartool.charts.config.ChartDefinition;
import za.co.rin.chartool.charts.datasource.ChartDataSource;
import za.co.rin.chartool.charts.datasource.KeyValueDataItem;
import za.co.rin.chartool.charts.datasource.PointDataItem;
import za.co.rin.chartool.charts.json.KeyValueJsonWrapper;
import za.co.rin.chartool.charts.json.PointJsonWrapper;

import java.util.List;

@Component
public class ScatterChartScriptGenerator implements ChartScriptGenerator {

    @Autowired
    private ChartDataSource chartDataSource;

    @Autowired
    private ChartColorManager chartColorManager;

    private static final String CHART_SCRIPT_TEMPLATE = "js_templates/scatter_chart.template";

    public String getChartScript(ChartDefinition chartDefinition) {
        return createChartScript(chartDefinition);
    }

    private String createChartScript(ChartDefinition chartDefinition) {
        List<PointDataItem> dataItems = chartDataSource.getPointDataItems(chartDefinition);
        PointJsonWrapper jsonWrapper = new PointJsonWrapper(dataItems);

        String color = chartColorManager.getChartColorsJson(1);

        //TODO:  Encapsulate template logic in template class.
        String template = TemplateReader.getTemplate(CHART_SCRIPT_TEMPLATE);

        return template
                .replaceAll("\\$CHART_ID", chartDefinition.getId())
                .replaceAll("\\$CHART_NAME", chartDefinition.getName())
                .replaceAll("\\$DATASET_LABEL", chartDefinition.getLabel())
                .replaceAll("\\$BACKGROUND_COLOR", color)
                .replaceAll("\\$DATA", jsonWrapper.getData())
                .replaceAll("\\$LOAD_FUNCTION", chartDefinition.getLoadFunction());
    }

    protected void setChartColorManager(ChartColorManager chartColorManager) {
        this.chartColorManager = chartColorManager;
    }

    protected void setChartDataSource(ChartDataSource chartDataSource) {
        this.chartDataSource = chartDataSource;
    }
}