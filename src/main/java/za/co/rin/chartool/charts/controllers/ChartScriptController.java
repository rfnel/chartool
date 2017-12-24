package za.co.rin.chartool.charts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import za.co.rin.chartool.charts.config.ChartDefinition;
import za.co.rin.chartool.charts.config.DashboardManager;
import za.co.rin.chartool.charts.generators.ChartScriptGenerator;
import za.co.rin.chartool.charts.generators.ChartScriptGeneratorFactory;

@Controller
public class ChartScriptController {

    @Autowired
    private ChartScriptGeneratorFactory chartScriptGeneratorFactory;

    @Autowired
    private DashboardManager dashboardManager;

    @RequestMapping("/getChartJS")
    @ResponseBody
    private String getChartJS(@RequestParam String dashboardId, @RequestParam String chartId) {
        ChartDefinition chartDefinition = dashboardManager.getChartDefinition(dashboardId, chartId);

        ChartScriptGenerator chartScriptGenerator = chartScriptGeneratorFactory.getChartScriptGenerator(chartDefinition);
        return chartScriptGenerator.getChartScript(chartDefinition);
    }
}