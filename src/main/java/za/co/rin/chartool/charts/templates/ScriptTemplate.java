package za.co.rin.chartool.charts.templates;

public class ScriptTemplate {

    private String text;

    public ScriptTemplate(String text) {
        this.text = text;
    }

    public ScriptTemplate set(String field, String value) {
        this.text = this.text.replaceAll("\\$" + field, value);

        return this;
    }

    public String toScriptText() {
        return this.text;
    }
}
