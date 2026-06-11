package com.example.mybatis.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
    int status,
    String code,
    String message,
    List<FieldErrorDetail> errors,
    LocalDateTime timestamp) {

  public static ErrorResponse of(ErrorCode errorCode) {
    return new ErrorResponse(
        errorCode.getHttpStatus().value(),
        errorCode.getCode(),
        errorCode.getMessage(),
        List.of(),
        LocalDateTime.now());
  }

  public static ErrorResponse of(ErrorCode errorCode, List<FieldErrorDetail> errors) {
    return new ErrorResponse(
        errorCode.getHttpStatus().value(),
        errorCode.getCode(),
        errorCode.getMessage(),
        errors,
        LocalDateTime.now());
  }

  public record FieldErrorDetail(
      String field,
      String value,
      String reason) {
  }
}
