package com.qaprosoft.module.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.qaprosoft.module.controllers","com.qaprosoft.module.controllers"})
public class Application {
	public static void main(String[] args) throws Exception {
		 SpringApplication.run(Application.class, args);
	}
}
