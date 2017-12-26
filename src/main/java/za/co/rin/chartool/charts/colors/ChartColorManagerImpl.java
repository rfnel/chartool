package za.co.rin.chartool.charts.colors;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class ChartColorManagerImpl implements ChartColorManager {

    private List<String> colors;

    @PostConstruct
    public void initChartColorList() {
        loadColorsFromFile();
    }

    @Override
    public String getChartColorsJson(int dataItemCount) {
        List<String> chartColors;

        if (dataItemCount >= colors.size()) {
            chartColors = colors;
        } else {
            chartColors = colors.subList(0, dataItemCount);
        }
        
        return String.join(",", chartColors);
    }

    private void loadColorsFromFile() {
        colors = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("colors/colors.txt")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                colors.add("'" + line + "'");
            }
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to read colors file.");
        }
    }
}
