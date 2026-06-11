package com.example.mybatis.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "입력 형태가 올바르지 않습니다."),
  POST_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "일치하는 게시글이 존재하지 않습니다."),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S001", "서버 내부에 예기치 않은 오류가 발생했습니다.");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  // ErrorCode(HttpStatus httpStatus, String code, String message){
  // this.httpStatus = httpStatus;
  // this.code = code;
  // this.message = message;
  // }
}
