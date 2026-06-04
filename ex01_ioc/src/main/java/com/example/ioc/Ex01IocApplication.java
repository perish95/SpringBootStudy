package com.example.ioc; //Base Package를 벗어나는 자바 파일은 스프링부트가 읽을 수 없다.

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 스타터팩과 같은 어노테이션 여기에 ComponentScan이 숨어있다. 원래는 명시해야 한다.
public class Ex01IocApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ex01IocApplication.class, args);
	}

}
