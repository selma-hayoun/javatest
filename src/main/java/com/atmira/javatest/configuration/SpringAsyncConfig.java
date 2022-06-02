package com.atmira.javatest.configuration;

import com.atmira.javatest.exception.CustomAsyncExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Slf4j
@Configuration
@EnableAsync
public class SpringAsyncConfig extends AsyncConfigurerSupport {
//public class SpringAsyncConfig extends AsyncConfigurer {

//    //Ejecutor para todas las peticiones asíncronas
//    @Override
//    public Executor getAsyncExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(2);
//        executor.setMaxPoolSize(2);
//        executor.setQueueCapacity(500);
//        executor.setThreadNamePrefix("AsyncThread-");
//        executor.initialize();
//        return executor;
//    }
//
//    //Gestión de excepciones, dado que estas no son propagadas por el hilo
//    @Override
//    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
//        return new CustomAsyncExceptionHandler();
//    }


    @Override
    public Executor getAsyncExecutor() {//Para todos los métodos asíncronos
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("AsyncThread-");
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {//Solamente para los métodos void
//        return super.getAsyncUncaughtExceptionHandler();

//        return (throwable, method, obj)->{
//            log.error("Exception Caught in Thread - " + Thread.currentThread().getName());
//            log.error("Exception message - " + throwable.getMessage());
//            log.error("Method name - " + method.getName());
//
//            for (Object param : obj) {
//                log.error("Parameter value - " + param);
//
//            }
//        };

        return new CustomAsyncExceptionHandler();

    }
}
