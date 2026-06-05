package com.example.response.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.response.dto.UserResponse;

//@Controller
@RestController // @Controller + @ResponseBody
@RequestMapping("/api/users") // 공통주소
public class ResponseController {
  // Json 문자열 응답
  @GetMapping("/v1")
  // @ResponseBody // 반환 값을 ViewResolver가 View로 해석하지 않게 해줌
  public String responseString() {
    String jsonString = "{\"name\":\"홍길동\", \"age\":30}";
    return jsonString;
  }

  // 자바 객체 응답 (MessageConverter인 Jackson이 JSON 문자열로 자동 변환)
  @GetMapping("/v2")
  @ResponseBody
  public UserResponse responseObject() {
    return new UserResponse("사만다", 40);
  }

  // 응답 전용 객체 ResponseEntity<T>
  // 1. HTTP 상태 코드 반환 가능
  // 2. 응답 본문 작성 가능
  // 3. @ResponseBody 명시 불필요
  @GetMapping("/v3")
  public ResponseEntity<Map<String, String>> responseEntity() {
    // public ResponseEntity<UserResponse> responseEntity() { // 정상응답 시그니쳐
    // 정상 응답
    // return ResponseEntity.ok(new UserResponse("제시카", 20));

    // 예외 응답1 (미리 완성된 축약 메서드 활용)
    // return ResponseEntity.badRequest().body(Map.of("message", "잘못된 요청"));

    // 예외 응답2 (완성되지 않은 일반 예외)
    // return new ResponseEntity<>(Map.of("message", "잘못된 요청"),
    // HttpStatus.BAD_REQUEST);

    // 예외 응답3
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "권한 없음"));
  }
}
