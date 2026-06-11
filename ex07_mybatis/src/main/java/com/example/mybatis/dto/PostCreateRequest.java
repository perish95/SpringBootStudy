package com.example.mybatis.dto;

import jakarta.validation.constraints.NotNull;

public record PostCreateRequest(
    @NotNull(message = "작성자의 아이디는 필수 항목입니다.") Long userId, // PK는 참조타입으로 적어서 NULL체크를 유리하게 한다.

    @NotNull(message = "게시글 제목은 필수 항목입니다.") String title,

    String content) {

}
