package com.xyz;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger configuration class
 * 
 * @author Roberto Crespo
 * @Since 1.0.0
 */

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

	@Bean(name="expendedoraApi")
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo()).select().paths(paths())
				.apis(RequestHandlerSelectors.any()).build().useDefaultResponseMessages(false);				
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Expendedora Service")
				.version("1.0").license("Apache License Version 2.0").build();
	}

	private Predicate<String> paths() {
		return regex("/api/.*");
	}
}
