package za.co.rin.chartool.charts.json;

import za.co.rin.chartool.charts.datasource.PointDataItem;
import java.util.List;

public class PointJsonWrapper {

    private String data;

    public PointJsonWrapper(List<PointDataItem> dataItems) {
        initFields(dataItems);
    }

    private void initFields(List<PointDataItem> dataItems) {
        StringBuilder data = new StringBuilder();

        for (int i = 0; i < dataItems.size(); i++) {
            PointDataItem pointDataItem = dataItems.get(i);

            data.append("{x: " + pointDataItem.getX() + ", y: " + pointDataItem.getY() + "}");

            if (i < dataItems.size() - 1) {
                data.append(",");
            }
        }

        this.data = data.toString();
    }

    public String getData() {
        return data;
    }
}
