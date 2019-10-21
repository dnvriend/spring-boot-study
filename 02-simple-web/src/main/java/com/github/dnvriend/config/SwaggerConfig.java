package com.github.dnvriend.config;

import com.google.common.collect.ImmutableSet;
import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * We need a SwaggerConfig to enable Swagger
 *
 * Resources that need to be accessible by swagger-ui:
 *
 * <ul>
 *     <li>/swagger-resources</li>
 *     <li>/swagger-ui.html</li>
 *     <li>/csrf</li>
 *     <li>/v2/api-docs</li>
 * </ul>
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * We need to register a Docket that will be used by SpringFox
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
                .pathMapping("/")
                .protocols(ImmutableSet.of("http", "https"))
                .apiInfo(apiInfo("title", String.format("%s - %s", "version", "gitCommit"), "description"));
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
                Collections.emptyList()
        );
    }
}
