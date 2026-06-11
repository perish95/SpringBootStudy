package com.example.mybatis.dto;

import java.util.List;

//페이징 처리가 필요할 경우
public record PageResponse<T>(
        List<T> contents,
        int page,
        int size,
        int totalPages,
        long totalElements,
        String sort) {

}
