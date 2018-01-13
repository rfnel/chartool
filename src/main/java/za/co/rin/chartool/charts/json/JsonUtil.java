package za.co.rin.chartool.charts.json;

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

    public static String valuesToJsonList(List<Number> values) {
        StringBuilder json = new StringBuilder();

        for (int i = 0; i < values.size(); i++) {
            json.append(values.get(i));

            if (i < values.size() - 1) {
                json.append(",");
            }
        }

        return json.toString();
    }
}
