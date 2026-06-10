package com.example.mybatis.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public record PostCreateRequest(
    @NotBlank(message = "작성자의 아이디는 필수 항목입니다.") 
    Long user_id, //PK는 참조타입으로 적어서 NULL체크를 유리하게 한다.

    @NotBlank(message = "게시글 제목은 필수 항목입니다.")
    String title,
    
    String content
) {

}
