package com.atmira.javatest.configuration;

import com.atmira.javatest.interceptor.AsteroidsRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class JavaTestConfig implements WebMvcConfigurer {

    @Autowired
    private AsteroidsRequestInterceptor asteroidsRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(asteroidsRequestInterceptor)
                .addPathPatterns("/asteroids");
//        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
