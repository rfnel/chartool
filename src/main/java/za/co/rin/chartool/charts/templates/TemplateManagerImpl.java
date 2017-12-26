package za.co.rin.chartool.charts.templates;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class TemplateManagerImpl implements TemplateManager {

    private Map<String, String> cachedTemplates = new HashMap<>();

    @Override
    public ScriptTemplate getScriptTemplate(String path) {
        String templateText = getTemplate(path);

        return new ScriptTemplate(templateText);
    }

    @Override
    public DatasetTemplate getDatasetTemplate(String path) {
        String templateText = getTemplate(path);

        return new DatasetTemplate(templateText);
    }

    private String getTemplate(String path) {
        String templateText = this.cachedTemplates.get(path);

        if (templateText == null) {
            templateText = loadTemplate(path);

            this.cachedTemplates.put(path, templateText);
        }
        return templateText;
    }

    private String loadTemplate(String path) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(TemplateManagerImpl.class.getClassLoader().getResourceAsStream(path)))) {
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
