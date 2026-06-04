package com.example.ioc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ioc.dto.UserDTO;
import com.example.ioc.service.EmailNotificationService;
import com.example.ioc.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
  // 알림 서비스 객체
  // private NotificationService notificationService = new
  // EmailNotificationService(); 결합도를 낮추는 코드
  // EmailNotificationService에 @Component를 통해서 new 키워드를 사용하지 않음(IoC)
  // DI 3가지 주입 방법
  // 1. 필드 주입(필드 마다)
  /*
   * @Autowired
   * private NotificationService notificationService;
   */

  // 2. Setter 주입 (Setter의 매개변수로 주입) : 1번에 비해 Autowired를 적게 사용할 수 있다.
  /*
   * private NotificationService notificationService;
   * 
   * @Autowired
   * public void setNotificationService(NotificationService notificationService) {
   * this.notificationService = notificationService;
   * }
   */

  // 3.Constructor 주입 (Constructor의 매개변수로 주입)
  /*
   * private NotificationService notificationService;
   * 
   * @Autowired // spring 4.3 이상에서 생성자가 1개인 경우 Autowired는 생략가능하다.
   * public UserController(NotificationService notificationService) {
   * this.notificationService = notificationService;
   * }
   */

  // 실무 DI
  // 객체 NPE 방지, 객체 불변성 유지, 순환 참조(A->B, B->A) 방지하기 위해서
  // 필드 선언 시 final 키워드 추가 -> final이 값이 채워지는 2가지 -> 1. 처음 할당할 때, 2. 생성자
  // NotificationService 타입의 빈이 2개 이상 있으면 이름으로 구분 가능
  // @Qualifier("SmsNotificationService")
  private final NotificationService notificationService;
  private final ObjectMapper objectMapper;

  // public UserController(NotificationService notificationService) {
  // this.notificationService = notificationService;
  // }

  // 회원 가입 시 알림 서비스 사용
  @RequestMapping("/join")
  public void createUser() {
    notificationService.sendNotification("반갑습니다!");
  }

  // 회원 정보 수정 시 알림
  @RequestMapping("/modify")
  public void modifyUser() {
    notificationService.sendNotification("수정되었습니다!");
  }

  @RequestMapping("/json-test")
  public void jsonTest() {
    try {
      // 1. 자바 객체 -> JSON 문자열(직렬화)
      UserDTO dto = new UserDTO("홍길동", 30);
      String jsonString = objectMapper.writeValueAsString(dto);

      System.out.println("생성된 json " + jsonString);
      // 2. JSON 문자열 -> 자바객체 (역직렬화)
      String inputJson = "{\"name\":\"김철수\", \"age\":40}";
      UserDTO resultDTO = objectMapper.readValue(inputJson, UserDTO.class);

      System.out.println("생성된 DTO: " + resultDTO);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("[예외 발생 사유]: " + e.getMessage());
    }
  }

}
