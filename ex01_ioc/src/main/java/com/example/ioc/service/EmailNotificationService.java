package com.example.ioc.service;

import org.springframework.stereotype.Component;

@Component // new EmailNotificationService()를 대체함
public class EmailNotificationService implements NotificationService {

  @Override
  public void sendNotification(String Message) {
    System.out.println("[이메일 발송] " + Message);

  }
}
