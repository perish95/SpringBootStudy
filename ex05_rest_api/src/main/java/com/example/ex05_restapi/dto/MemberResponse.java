package com.example.ex05_restapi.dto;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record MemberResponse(
    Long id,
    String email,
    LocalDateTime createdAt) {
}
