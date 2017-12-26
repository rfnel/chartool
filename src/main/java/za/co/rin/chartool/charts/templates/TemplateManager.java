package za.co.rin.chartool.charts.templates;

public interface TemplateManager {

    ScriptTemplate getScriptTemplate(String path);
    DatasetTemplate getDatasetTemplate(String path);
}
