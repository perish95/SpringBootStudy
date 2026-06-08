package com.example.ex05_restapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex05_restapi.dto.MemberRequest;
import com.example.ex05_restapi.dto.MemberResponse;
import com.example.ex05_restapi.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/members")
@RequiredArgsConstructor
@RestController
public class MemberApiController {
  private final MemberService memberService;

  // 1. 등록
  // @RequestBody가 없으면 null이 날라옴
  // 데이터를 항상 Body로 담아서 날릴 것
  @PostMapping
  public ResponseEntity<MemberResponse> createMember(@RequestBody MemberRequest request) {
    MemberResponse savedMember = memberService.save(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedMember);
  }
}
