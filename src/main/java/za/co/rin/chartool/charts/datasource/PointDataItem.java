package za.co.rin.chartool.charts.datasource;

public class PointDataItem implements DataItem {

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

    @Override
    public String getDataSetLabel() {
        return null;
    }
}
