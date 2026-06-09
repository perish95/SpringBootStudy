package com.example.valid.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
  // 400 Bad Request - Valid 실패
  // 개선사항:이메일이 문제인지, 이름이 문제인지 구체적으로 알려주는 것이 아쉬운점 -> BindingResult 값을 가져와서 메꾼다.
  // 틀린 것도 여러 개일 수도 있기때문에 리스트로 반환하는 것이 좋다.
  INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "올바르지 않은 입력 값입니다."),

  // 404 Not Found
  MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", "존재하지 않는 회원입니다."),

  // 409 Conflict - 중복체크 실패
  DUPLICATE_EMAIL(HttpStatus.CONFLICT, "M002", "이미 존재하는 이메일입니다."),

  // 500 Internal Server Error
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S001", "서버 내부 오류가 발생했습니다.");

  private HttpStatus status;
  private String code;
  private String message;

  ErrorCode(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

}
