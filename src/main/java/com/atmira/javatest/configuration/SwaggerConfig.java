package com.atmira.javatest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "ATMIRA - Prueba práctica: Backend - Java",
                "Exponer un endpoint que reciba un planeta como parámetro y que devuelva un listado en formato json con el top 3 de asteroides más grandes \n" +
                        "con potencial riesgo de impacto en dicho planeta en los próximos 7 días. En caso de que no haya 3 o más planetas bajo estas condiciones, \n" +
                        "devolver los que sea que apliquen",
                "API 1.0",
                "#",
                new Contact(
                        "Selma Hayoun Caballero",
                        "https://es.linkedin.com/in/selma-problem-solving-lover",
                        "selma.hayoun.caballero@gmail.com"),
                "License of API", "#", Collections.emptyList());
    }
}
