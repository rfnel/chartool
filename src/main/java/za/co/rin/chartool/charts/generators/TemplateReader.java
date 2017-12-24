package za.co.rin.chartool.charts.generators;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TemplateReader {

    //TODO:  Cache templates.
    //TODO:  Convert to interface.

    public static String getTemplate(String path) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(TemplateReader.class.getClassLoader().getResourceAsStream(path)))) {
            StringBuilder template = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                template.append(line);
                template.append("\n");
            }

            return template.toString();
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to read template file: " + path);
        }
    }
}
