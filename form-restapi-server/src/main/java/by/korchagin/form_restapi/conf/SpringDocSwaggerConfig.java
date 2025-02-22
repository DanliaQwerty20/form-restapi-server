package by.korchagin.form_restapi.conf;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocSwaggerConfig {
    @Bean
    public GroupedOpenApi controllerApi() {
        return GroupedOpenApi.builder().group("controller-api").packagesToScan("by.korchagin.form_restapi.controller").build();
    }
}
