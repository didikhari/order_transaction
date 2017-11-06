package com.salestock.didik;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {"com.salestock.didik"})
public class SwaggerConfig {
    @Bean
    public Docket api() {
    	ArrayList<SecurityScheme> securitySchemes = new ArrayList<SecurityScheme>();
		securitySchemes.add(new BasicAuth("basicAuth"));
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build().securitySchemes(securitySchemes).ignoredParameterTypes(Principal.class);
    }

}
