package com.example.office_web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@MapperScan("com.example.office_web.mapper")
@SpringBootApplication
@ImportResource({
		"classpath:spring-mvc.xml"
}) //导入xml配置项
public class OfficeWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(OfficeWebApplication.class, args);
	}

}
