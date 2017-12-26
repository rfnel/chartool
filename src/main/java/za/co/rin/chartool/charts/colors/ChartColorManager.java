package za.co.rin.chartool.charts.colors;

public interface ChartColorManager {

    String getChartColorsJson(int colorOffset, int dataItemCount);
    String getChartColorsJson(int dataItemCount);
}
