package com.atmira.javatest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
