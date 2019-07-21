package com.github.dnvriend.config;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import io.vavr.collection.*;

/**
 * We need a SwaggerConfig to enable Swagger
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * We need to register a Docket that will be used by SpringFox
     */
    @Bean
    public Docket api(
            @Value("${info.app.name}") String title,
            @Value("${info.app.version}") String version,
            @Value("${info.app.description}") String description,
            @Value("${info.git.commit.id.abbrev}") String gitCommit) {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
                .pathMapping("/")
                .protocols(List.of("https").toJavaSet())
                .apiInfo(apiInfo(title, String.format("%s - %s", version, gitCommit), description));
    }

    private ApiInfo apiInfo(String title, String version, String description) {
        return new ApiInfo(
                title,
                description,
                version,
                "termsOfService",
                new Contact("name", "url", "email"),
                "license",
                "licenseUrl",
                List.<VendorExtension>empty().asJava()
        );
    }
}
