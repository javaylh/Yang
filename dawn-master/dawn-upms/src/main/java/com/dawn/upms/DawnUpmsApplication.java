package com.dawn.upms;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

/**
 * ---------------------------
 * 权限管理启动类 (DawnUpmsApplication)
 * ---------------------------
 * @author： ylh
 * 时间： 2019-10-16 15:20:00
 * ---------------------------
 */
@ComponentScan(basePackages = {"com.dawn.**"})
@SpringBootApplication(scanBasePackages = {"com.dawn.**"})
public class DawnUpmsApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(DawnUpmsApplication.class)
				.listeners((ApplicationListener<ApplicationEnvironmentPreparedEvent>) event -> {
					Environment environment = event.getEnvironment();
					int port = environment.getProperty("embedded.zookeeper.port", int.class);
					new EmbeddedZooKeeper(port, false).start();
				})
				.run(args);
	}
}
