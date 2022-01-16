package com.saurabh.myboot;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.saurabh.operation.ReadUserDetail;

@EnableJpaRepositories(basePackages="com.saurabh.repository")
@SpringBootApplication()
@ComponentScan(basePackages={"com.saurabh.pojo","com.saurabh.service","com.saurabh.repository",
	            "com.saurabh.filter","com.saurabh.security","com.saurabh.operation",
	            "com.saurabh.filter","com.saurabh.controllers", "com.saurabh.logger"})
@EntityScan(basePackages="com.saurabh.entity")
public class MyBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyBootApplication.class, args);
		new ReadUserDetail().readUserDetail();
}
	}


