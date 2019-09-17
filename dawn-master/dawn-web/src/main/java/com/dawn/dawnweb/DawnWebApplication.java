package com.dawn.dawnweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.dawn.**"})
@SpringBootApplication(scanBasePackages = {"com.dawn.**"})
public class DawnWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(DawnWebApplication.class, args);
	}
}
