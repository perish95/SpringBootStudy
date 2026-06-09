package com.example.valid.dto;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record MemberDto(
    Long id,
    String username,
    String email,
    LocalDateTime creatdAt) {

}
