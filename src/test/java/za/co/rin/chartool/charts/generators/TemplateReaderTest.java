package za.co.rin.chartool.charts.generators;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TemplateReaderTest {

    @Test
    public void testGetTemplate() throws Exception {
        String expectedResult = "function $LOAD_FUNCTION() {\n" +
                                "    TEST, TEST, TEST\n" +
                                "}\n";

        String template = TemplateReader.getTemplate("js_templates/test.template");

        assertThat(template, is(equalTo(expectedResult)));
    }
}