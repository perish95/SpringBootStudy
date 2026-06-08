package com.example.aop.service;

import org.springframework.stereotype.Service;

@Service // @Component를 가지고 있는 어노테이션: service에서 사용하는 전용 Component라고 생각하면 편한
public class OrderService {

  // 실제 업무 처리(비즈니스 메서드:타켓 메서드)
  public String createOrder(String itemId) {
    System.out.println("[주문 생성 메서드 시작] 주문 아이템: " + itemId);
    try {
      Thread.sleep(1000); // 1초 지연

    } catch (Exception e) {
      Thread.currentThread().interrupt();
    }
    System.out.println("[주문 생성 메서드 종료]");

    return "Order-" + itemId;
  }
}
