package za.co.rin.chartool.charts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import za.co.rin.chartool.charts.config.DashboardDefinition;
import za.co.rin.chartool.charts.config.DashboardManager;

@Controller
public class DashboardController {

    @Autowired
    private DashboardManager dashboardManager;

    @RequestMapping("/dashboard")
    public String requestDashboard(@RequestParam String id, Model model) {
        DashboardDefinition dashboardDefinition = dashboardManager.getDashboardDefinition(id);

        model.addAttribute("dashboard", dashboardDefinition);

        return "dashboard";
    }


}
