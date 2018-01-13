package za.co.rin.chartool.charts.data;

public class PointDataItem {

    private final Number x;
    private final Number y;

    public PointDataItem(Number x, Number y) {
        this.x = x;
        this.y = y;
    }

    public Number getX() {
        return x;
    }

    public Number getY() {
        return y;
    }
}
