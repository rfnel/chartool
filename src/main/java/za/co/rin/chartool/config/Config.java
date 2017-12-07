package za.co.rin.chartool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import za.co.rin.chartool.generated.Dashboard;
import za.co.rin.chartool.generated.Charts;

@Configuration
public class Config {

    @Bean
    public Unmarshaller marshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setClassesToBeBound(Charts.class, Dashboard.class);
        jaxb2Marshaller.setSchema(new ClassPathResource("charts.xsd"));

        return jaxb2Marshaller;
    }
}
