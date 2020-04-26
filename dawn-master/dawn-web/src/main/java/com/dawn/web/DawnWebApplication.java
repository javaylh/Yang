package com.dawn.web;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
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
public class DawnWebApplication implements CommandLineRunner {

	@Autowired
	private ApplicationContext appCtx;

	@Autowired
	private StringEncryptor codeSheepEncryptorBean;

	public static void main(String[] args) {
		SpringApplication.run(DawnWebApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Environment environment = appCtx.getBean(Environment.class);

		// 首先获取配置文件里的原始明文信息
		String mysqlOriginPswd = environment.getProperty("spring.datasource.druid.password");
		String redisOriginPswd = environment.getProperty("spring.redis.password");

		// 加密
		String mysqlEncryptedPswd = encrypt( mysqlOriginPswd );
		String redisEncryptedPswd = encrypt( redisOriginPswd );

		// 打印加密前后的结果对比
		System.out.println( "MySQL原始明文密码为：" + mysqlOriginPswd );
		System.out.println( "Redis原始明文密码为：" + redisOriginPswd );
		System.out.println( "====================================" );
		System.out.println( "MySQL原始明文密码加密后的结果为：" + mysqlEncryptedPswd );
		System.out.println( "Redis原始明文密码加密后的结果为：" + redisEncryptedPswd );
		// 打印解密后的结果
		System.out.println( "MySQL原始明文密码为：" + mysqlOriginPswd );
		System.out.println( "Redis原始明文密码为：" + redisOriginPswd );
	}

	/**
	 * 加密
	 * @param originPassord
	 * @return
	 */
	private String encrypt( String originPassord ) {
		String encryptStr = codeSheepEncryptorBean.encrypt( originPassord );
		return encryptStr;
	}

	/**
	 * 解密
	 * @param encryptedPassword
	 * @return
	 */
	private String decrypt( String encryptedPassword ) {
		String decryptStr = codeSheepEncryptorBean.decrypt( encryptedPassword );
		return decryptStr;
	}

}
