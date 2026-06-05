package com.example.request.controller;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.example.request.dto.UserRequest;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/users") // 공통주소는 반복됨으로 한 번만 작성하도록 한다.
public class RequestController {
  // 테스트 요청 주소
  // http://localhost:8080/api/users/v1?name="홍길동"&age=30
  // 요청 파라미터 1 (HttpServletRequest 활용하기)
  @GetMapping("/v1")
  public void legacy(HttpServletRequest request) {
    // 모든 요청 파라미터는 String 타입으로 전달
    String name = request.getParameter("name");
    String strAge = request.getParameter("age");

    // 파라미터가 전달되지 않는 경우
    // 1. 값이 없는 경우 : 빈 문자열("") ex)name=&age=30
    // 2. 파라미터가 없는 경우 : null ex)?age=30

    // isBlank와 isEmpty의 차이 -> blank는 빈 문자열과 공백까지 검사해준다. isEmpty보다 더 넓은 범위
    int age = 0;
    if (strAge != null && !strAge.isBlank()) {
      age = Integer.parseInt(strAge);
    }
    System.out.println(name + " " + age);
  }

  // 요청 파라미터 2(@RequestParam) 스프링지원 -> 편하다!
  // @RequestParam : 필수 동작이므로 없으면 400 상태코드로 뱉는다. -> required를 통해 필수 여부를 설정 가능
  // defaultValue는 반드시 String으로 설정해야함. 어차피 RequestParam이 자동으로 형변환해주기 때문에 괜춘
  // 요청 파라미터가 적을 때 사용하기 용이함
  @GetMapping("/v2")
  public void requestParam( // String으로 안 받고 자동 변환해줌
      @RequestParam("name") String name,
      @RequestParam(value = "age", required = false, defaultValue = "0") int age) {
    System.out.println(name + " " + age);
  }

  // 요청 파라미터 3(커맨드 객체 이용 - 파라미터를 필드로 가진 객체)
  // Setter(생성자)로 데이터를 입력, ToString을 통해 출력
  // 생성자로 할 경우 파라미터를 못 찾을 수도 있다. 스프링부트의 이슈다. setter로 가자
  @GetMapping("/v3")
  public void commandObject(UserRequest request) {
    System.out.println("[GET]:" + request);
  }

  // 요청 본문 (요청을 본문에 담아서 보내는 POST방식)
  // 클라이언트 : JSON, 서버 : 자바 객체
  // 스프링 부트의 MessageConverter는 Jackson이 기본 설정(Spring Web)
  // 클라이언트의 insert과정에서 사용됨
  @PostMapping("/v4")
  public void requestBody(@RequestBody UserRequest request) { // Jackson이 해줌
    System.out.println("[POST]:" + request);
  }

  // 파일 첨부 요청
  // Method:POST
  // EncType:multipart/form-data
  // 부트 서버는 MultipartFile 파라미터로 파일을 받음
  // 파일을 제외한 나머지 파라미터는 커맨드 객체로 처리 추천
  // @RequestPart가 받기때문에 @RequestBody를 사용하면 안된다.
  // MultipratFile을 List로 만들어서 받으면 다중 첨부 파일이 된다.@GetMapping("path")
  public String getMethodName(@RequestParam String param) {
    return new String();
  }    

  @PostMapping("/v5")
  public void fileAttach(
      @RequestPart("profile") MultipartFile profile,
      UserRequest request) {
    if (profile.isEmpty()) {
      System.out.println("첨부 파일이 없습니다.");
      return;
    }

    System.out.println("파일명: " + profile.getOriginalFilename());
    System.out.println("파일크기: " + profile.getSize() + "Bytes");
    System.out.println("텍스트 데이터: " + request);
  }
}
