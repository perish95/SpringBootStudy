package com.example.mybatis.dto;

import java.time.LocalDateTime;

public record PostResponse(
    Long id,
    String title,
    String content,
    LocalDateTime createdAt,
    Author author) {
  public record Author(
      Long id,
      String email,
      String nickname) {
  }
}
