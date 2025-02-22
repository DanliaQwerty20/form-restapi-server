package by.kochagin.teacher_restapi.conf;

import feign.Contract;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.cloud.openfeign.clientconfig.FeignClientConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocSwaggerConfig {
    @Bean
    public GroupedOpenApi controllerApi() {
        return GroupedOpenApi.builder().group("controller-api").packagesToScan("by.kochagin.teacher_restapi.controller").build();
    }

    @Bean
    public FeignClientConfigurer feignClientConfigurer() {
        return new FeignClientConfigurer() {
            @Override
            public boolean inheritParentConfiguration() {
                return false;
            }
        };
    }
}
