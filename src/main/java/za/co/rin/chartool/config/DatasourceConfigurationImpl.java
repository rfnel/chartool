package za.co.rin.chartool.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

@Component
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties (prefix = "chartool-datasource-bup")
public class DatasourceConfigurationImpl implements DatasourceConfiguration{


    private HashMap<String, Object> build = new HashMap<String, Object>();

    public HashMap<String, Object> getBuild() {
        return this.build;
    }
    public void setBuild(HashMap<String, Object> build) {
        this.build=build;
    }

    @Bean(name = "datasources")
    public Map<String, JdbcTemplate> getCharToolDatasources() {
        Map <String, JdbcTemplate> datasources = new HashMap<String,JdbcTemplate>();
        System.out.println("Creating datasources");
        for (Map.Entry<String, Object> current : build.entrySet()) {
//            Field props = DataSourceBuilder.class.getDeclaredField("properties");
//            props.setAccessible(true);
            DataSourceBuilder builder = DataSourceBuilder.create();
//            Map<String, String> properties = (Map<String, String>) props.get(builder);

//            properties.put("defaultRowPrefetch", "1000");
//            properties.put("defaultBatchValue", "1000");
            ChartoolDatasource currentDef = new ChartoolDatasource(current.getValue());
            datasources.put(current.getKey(), new JdbcTemplate(DataSourceBuilder.create().driverClassName(
                    currentDef.getDriverClassName()).password(currentDef.getPassword()).username(currentDef.getUsername()).url(currentDef.getUrl()).build()));
        }
        return datasources;
    }


}
