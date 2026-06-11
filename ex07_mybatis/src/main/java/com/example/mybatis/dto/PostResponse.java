package com.example.mybatis.dto;

import java.time.LocalDateTime;

import com.example.mybatis.domain.Post;
import com.example.mybatis.domain.User;

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

  public static PostResponse from(Post post) {
    User user = post.getUser();

    return new PostResponse(
        post.getId(),
        post.getTitle(),
        post.getContent(),
        post.getCreatedAt(),
        user != null ? new Author(user.getId(), user.getEmail(), user.getNickname()) : null);
  }
}
