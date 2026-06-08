package com.example.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy // @Configuration과 함께 두기
@SpringBootApplication // @Configuration은 @SpringBootApplication 안에 있다.
public class Ex04AopApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ex04AopApplication.class, args);
	}

}
