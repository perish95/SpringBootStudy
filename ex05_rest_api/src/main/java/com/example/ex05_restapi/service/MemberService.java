package com.example.ex05_restapi.service;

import java.util.List;

import com.example.ex05_restapi.dto.MemberRequest;
import com.example.ex05_restapi.dto.MemberResponse;

public interface MemberService {
  MemberResponse save(MemberRequest request);

  List<MemberResponse> findAll();

  MemberResponse findById(Long id);

  MemberResponse update(MemberRequest request, Long id);

  void deleteById(Long id);
}
