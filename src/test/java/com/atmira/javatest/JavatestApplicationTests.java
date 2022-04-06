package com.atmira.javatest;

import static org.assertj.core.api.Assertions.assertThat;

import com.atmira.javatest.controller.AsteroidsController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JavatestApplicationTests {

	@Autowired
	private AsteroidsController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
