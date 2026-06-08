package com.example.aop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import com.example.aop.advice.TimeTraceAspect;

//이런 방법도 있다. 
//TimeTraceAsepct에 @Component가 권장사항이다.
// @EnableAspectJAutoProxy
// @Configuration
public class AopConfig {
  // @Bean
  // public TimeTraceAspect timeTraceAspect() {
  // return new TimeTraceAspect();
  // }
}
