package za.co.rin.chartool.charts.json;

import za.co.rin.chartool.charts.data.KeyValueDataItem;
import za.co.rin.chartool.charts.data.PointDataItem;

import java.util.List;

public class JsonUtil {

    public static String stringsToJsonList(List<String> strings) {
        StringBuilder json = new StringBuilder();

        for (int i = 0; i < strings.size(); i++) {
            json.append("'" + strings.get(i) + "'");

            if (i < strings.size() - 1) {
                json.append(",");
            }
        }

        return json.toString();
    }

    public static String valuesToJsonList(List<KeyValueDataItem> values) {
        StringBuilder json = new StringBuilder();

        for (int i = 0; i < values.size(); i++) {
            if (values.get(i) != null) {
                json.append(values.get(i).getValue());
            } else {
                json.append(0);
            }

            if (i < values.size() - 1) {
                json.append(",");
            }
        }

        return json.toString();
    }

    public static String pointsToJsonList(List<PointDataItem> dataItems) {
        StringBuilder json = new StringBuilder();

        for (int i = 0; i < dataItems.size(); i++) {
            PointDataItem pointDataItem = dataItems.get(i);
            json.append("{x: " + pointDataItem.getX() + ", y: " + pointDataItem.getY() + "}");

            if (i < dataItems.size() - 1) {
                json.append(",");
            }
        }

        return json.toString();
    }
}
