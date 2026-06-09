package com.example.ex05_restapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient.ResponseSpec;

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

  // 2. 전체 조회
  @GetMapping
  public ResponseEntity<List<MemberResponse>> getAllMembers() {
    List<MemberResponse> members = memberService.findAll();

    return ResponseEntity.ok(members);
  }

  // 3. 단건 조회
  // 상태반환을 ok, not found 2가지 상태로 함
  // try-catch가 되는 이유는 service에서 throws를 던졌기 때문
  @GetMapping("/{id}")
  public ResponseEntity<MemberResponse> getMemberById(@PathVariable("id") Long id) {
    try {
      MemberResponse foundMember = memberService.findById(id);
      return ResponseEntity.ok(foundMember);

    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  // 4. 회원 수정
  @PutMapping("/{id}")
  public ResponseEntity<MemberResponse> updateMember(
      @PathVariable("id") Long id,
      @RequestBody MemberRequest request) {
    try {
      MemberResponse updatedMember = memberService.update(request, id);

      return ResponseEntity.ok(updatedMember);
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  // 5. 회원 삭제
  // 제네릭에서 아무것도 안 보낼때는 Void를 사용한다.
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMember(@PathVariable("id") Long id) {
    try {
      memberService.deleteById(id);

      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }
}
