package ru.otus.helloworld.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
@Profile("swagger")
public class SwaggerConfig {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("otus hello")
            .description("API")
            .version("1.0")
            .build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
            .select()
            .apis(RequestHandlerSelectors.basePackage("ru.otus.helloworld.web"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo());
    }

}
