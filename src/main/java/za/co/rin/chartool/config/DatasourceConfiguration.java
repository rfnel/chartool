package za.co.rin.chartool.config;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface DatasourceConfiguration {
    HashMap<String, Object> getBuild();
    void setBuild(HashMap<String, Object> build);

    Map<String, JdbcTemplate> getCharToolDatasources();
}
