package com.beini;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Spring-Boot启动类
 * 
 * @author lb_chen
 */
@EnableCaching
@EnableSwagger2
@SpringBootApplication
//@EnableEurekaClient
@EnableDiscoveryClient
public class Application {

	public static void main(String[] args) {
		/* 简单启动spring boot的main方法 */
		// SpringApplication.run(Application.class,args);

		SpringApplication app = new SpringApplication(Application.class);
		app.setBannerMode(Mode.OFF);
		app.run(args);
	}
}
