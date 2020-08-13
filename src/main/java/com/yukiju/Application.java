package com.yukiju;

import org.apache.log4j.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {
	
static Logger logger = Logger.getRootLogger();
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println("system up and running");
	}

}
