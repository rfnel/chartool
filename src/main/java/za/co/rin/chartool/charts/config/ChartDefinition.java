package za.co.rin.chartool.charts.config;

public class ChartDefinition {

    private String id;
    private String name;
    private String description;
    private String type;
    private String query;

    private int index;

    private String loadFunction;

    public ChartDefinition() {
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getLoadFunction() {
        if (loadFunction == null) {
            loadFunction = "load_" + id + "_chart";
        }

        return loadFunction;
    }
}
