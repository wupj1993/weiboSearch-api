package com.boot.swagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableSwagger2
public class DataApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(DataApp.class, args);
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("信息查询")
                .description("ElasticSearch为数据库的查询")
                .contact("吴培基")
                .license("Apache License Version 2.0")
                .version("2.0")
                .build();
    }
    @Bean
    public SecurityScheme apiKey() {
        return new ApiKey("access_token", "accessToken", "header");
    }
    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("wupj")
                .apiInfo(apiInfo())
                .select()
                .apis( RequestHandlerSelectors.basePackage( "com.boot.swagger" ) )
                .build();
    }

}
