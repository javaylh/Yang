package com.dawn.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * ---------------------------
 * 启动类 (DawnWebApplication)
 * ---------------------------
 * @author： ylh
 * 时间： 2019-09-20 15:20:00
 * ---------------------------
 */
@EnableAsync
@EnableScheduling
@ComponentScan(basePackages = {"com.dawn.**"})
@SpringBootApplication(scanBasePackages = {"com.dawn.**"})
public class DawnWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(DawnWebApplication.class, args);
	}
}
