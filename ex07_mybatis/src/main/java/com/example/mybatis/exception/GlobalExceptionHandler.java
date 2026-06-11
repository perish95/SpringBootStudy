package com.example.mybatis.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // @RestController에서 발생하는 모든 예외를 가로챈 뒤 처리하는 클래스
public class GlobalExceptionHandler {
  // @Valid 검증 실패 시
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    BindingResult bindingResult = e.getBindingResult();
    List<ErrorResponse.FieldErrorDetail> fieldErrorDetail = bindingResult.getFieldErrors().stream()
        .map(error -> new ErrorResponse.FieldErrorDetail(
            error.getField(),
            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
            error.getDefaultMessage()))
        .collect(Collectors.toList());
    ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, fieldErrorDetail);

    return new ResponseEntity<>(errorResponse, ErrorCode.INVALID_INPUT_VALUE.getHttpStatus());
  }

  // CustomException 예외 처리기
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
    ErrorCode errorCode = e.getErrorCode();
    ErrorResponse errorResponse = ErrorResponse.of(errorCode);

    return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
  }

  // 나머지 모든 예외 처리
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception e) {
    ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
    return new ResponseEntity<>(errorResponse, ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus());
  }
}
