package com.geekshirt.order.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
//no encuentra esta clase
//import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
//@Import(BeanValidatorPluginsConfiguration.class) Arroja una excp
public class SwaggerConfig {

    @Bean
    public Docket apiDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build().apiInfo(getApiInfo());
    }

    //overview de que es y de quien es el servicio
    private ApiInfo getApiInfo(){
        return new ApiInfo("GeekShirt Order Service Api",
                "This Api provides all methods required for order management",
                "1.0",
                "TERMS OF SERVICE URL",
                new Contact("Centripio", "https://centripio.io", "maildeej"),
                "LICENSE",
                "LICENSE URL", Collections.emptyList());

    }

}
