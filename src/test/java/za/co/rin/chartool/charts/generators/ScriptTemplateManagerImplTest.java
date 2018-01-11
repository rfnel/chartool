package za.co.rin.chartool.charts.generators;

import org.junit.Test;
import za.co.rin.chartool.charts.templates.ScriptTemplate;
import za.co.rin.chartool.charts.templates.TemplateManagerImpl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ScriptTemplateManagerImplTest {

    private TemplateManagerImpl templateManager = new TemplateManagerImpl();

    @Test
    public void testGetScriptTemplate() throws Exception {
        String expectedResult = "function $LOAD_FUNCTION() {\n" +
                                "    TEST, TEST, TEST\n" +
                                "}\n";

        ScriptTemplate scriptTemplate = templateManager.getScriptTemplate("js_templates/test.template");

        assertThat(scriptTemplate.toScriptText(), is(equalTo(expectedResult)));
    }
}