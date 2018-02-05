package za.co.rin.config.chartool;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.rin.chartool.charts.data.JdbcChartDataSource;
import za.co.rin.chartool.config.ChartoolDatasource;
import za.co.rin.chartool.config.DatasourceConfiguration;
import za.co.rin.chartool.config.DatasourceConfigurationImpl;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class, classes = {DatasourceConfigurationImpl.class})
public class DatasourceConfigTest {


    @Autowired
    private DatasourceConfiguration myConfig;

    @Autowired
    @Qualifier("datasources")
    Map<String, JdbcTemplate> datasources;

    @Test
    public void testThatConfigFileReadsCorrectNumberOfDatasources(){
        assertThat("The INCORRECT number of datasources were read", myConfig.getBuild().size(),is(2));
        assertThat("The map contains the correct element", myConfig.getBuild().containsKey("db-main"), is(true));
        ChartoolDatasource ds = new ChartoolDatasource( myConfig.getBuild().get("db-main"));
        assertThat("The DB-Build element is a chartool datasource ", ds.getDriverClassName(), is("com.mysql.jdbc.Driver"));
    }

    @Test
    public void testThatTheCorrectNumberOfChartoolDatasourcesAreReturned (){
        assertThat("The wrong number of ChartoolDatasources are returned", myConfig.getCharToolDatasources().size(), is(2));
    }

    @Test
    public void testThatAutowiredComponentIsSetCorrectly() throws SQLException{
//        assertThat("Component init correct", datasources.size(), is(2));
//        SqlRowSet o = datasources.get("db-main").queryForRowSet("SELECT f.rating, c.name, COUNT(*)\n" +
//                "                FROM film_category fc INNER JOIN category c ON fc.category_id = c.category_id\n" +
//                "                INNER JOIN film f ON f.film_id = fc.film_id\n" +
//                "                GROUP BY f.rating, c.name;");
//        while (o.next()) {
//            System.out.println("Name: " + o.getString(1));
//        }
//        SqlRowSet rowset = datasources.get("db-main").queryForRowSet("SELECT COUNT(*) as BOB FROM film f");
//        assertThat("Datasource is connected", rowset.getInt("BOB"), is("1"));
    }
}
