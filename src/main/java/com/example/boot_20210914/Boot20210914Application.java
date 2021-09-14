package com.example.boot_20210914;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = { "com.example.controller" })
@SpringBootApplication
@EnableJpaRepositories(basePackages = { "com.example.repository" })
@EntityScan(basePackages = { "com.example.entity" })
public class Boot20210914Application {

	public static void main(String[] args) {
		SpringApplication.run(Boot20210914Application.class, args);
		System.out.println("시작");
	}

}
