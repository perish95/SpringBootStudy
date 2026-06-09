package com.example.valid.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.valid.dto.MemberCreateRequest;
import com.example.valid.dto.MemberDto;
import com.example.valid.dto.MemberUpdateRequest;
import com.example.valid.exception.CustomException;
import com.example.valid.exception.ErrorCode;

@Service
public class MemberService {
  private final Map<Long, MemberDto> store = new ConcurrentHashMap<>();
  private final AtomicLong sequence = new AtomicLong(0);

  public MemberService() {
    save(MemberCreateRequest.builder().username("kim").email("kim@test.com").build());
    save(MemberCreateRequest.builder().username("lee").email("lee@test.com").build());
    save(MemberCreateRequest.builder().username("park").email("park@test.com").build());
  }

  // Save
  public MemberDto save(MemberCreateRequest request) {
    // 이메일 중복 검증
    boolean isExistEmail = store.values().stream()
        .anyMatch(member -> member.email().equals(request.email())); // 1개라도 true -> true

    // 실무에서는 DB를 갔다와야하기 때문에 이렇게 안 짠다.
    if (isExistEmail) {
      throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
    }

    Long id = sequence.incrementAndGet();
    MemberDto member = MemberDto.builder()
        .id(id)
        .username(request.username())
        .email(request.email())
        .creatdAt(LocalDateTime.now())
        .build();
    store.put(id, member);

    return member;
  }

  // Read All
  public List<MemberDto> findAll() {
    return new ArrayList<>(store.values());
  }

  // Read One
  public MemberDto findById(Long id) {
    MemberDto foundMember = store.get(id);
    // 없는 회원 예외처리
    if (foundMember == null)
      throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);

    return foundMember;
  }

  // Update
  public MemberDto updateMember(Long id, @RequestBody MemberUpdateRequest request) {
    MemberDto foundMember = findById(id);
    MemberDto updatedMember = MemberDto.builder()
        .id(foundMember.id())
        .username(foundMember.username())
        .email(request.email())
        .creatdAt(foundMember.creatdAt())
        .build();

    return updatedMember;
  } 

  // Delete
  public void deleteMember(Long id) {
    findById(id);
    store.remove(id);
  }
}
