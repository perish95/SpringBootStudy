package com.example.ioc.service;

//문자 서비스든, 이메일 서비스든 교체가 쉬워지기 때문에 interface를 사용 -> 다형성
public interface NotificationService {
  void sendNotification(String Message); // interface는 public abstract 키워드를 생략가능
}
