package com.atmira.javatest.configuration;

import com.atmira.javatest.interceptor.AsteroidsRestTemplateInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
public class RestClientConfig {

    @Autowired
    private AsteroidsRestTemplateInterceptor asteroidsRestTemplateInterceptor;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder, AsteroidsRestTemplateInterceptor asteroidsRestTemplateInterceptor) {

        RestTemplate restTemplate = null;
        if (log.isDebugEnabled()) {
            //Nos permite leer el flujo de las pesticiones gestionadas por RESTTEMPLATE dos veces
            ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
            restTemplate = new RestTemplate(factory);
        } else {
            restTemplate = new RestTemplate();
        }

        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();

        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }

        interceptors.add(asteroidsRestTemplateInterceptor);

        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}
