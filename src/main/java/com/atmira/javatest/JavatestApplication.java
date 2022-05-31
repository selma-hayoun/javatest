package com.atmira.javatest;

import com.atmira.javatest.exception.CustomAsyncExceptionHandler;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
public class JavatestApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavatestApplication.class, args);
	}

////	@Bean("asyncExecutor")
//	@Bean(name = "ASYNCEXECUTOR")
//	public Executor taskExecutor() {
//		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//		executor.setCorePoolSize(2);
//		executor.setMaxPoolSize(2);
//		executor.setQueueCapacity(500);
//		executor.setThreadNamePrefix("AsyncThread-");
////		executor.setThreadGroupName("-AsyncCampions-");
//		executor.initialize();
//		return executor;
//	}

}
