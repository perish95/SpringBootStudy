package com.example.mybatis.service;

import org.springframework.stereotype.Service;

import com.example.mybatis.domain.Post;
import com.example.mybatis.dto.PostCreateRequest;
import com.example.mybatis.dto.PostResponse;
import com.example.mybatis.mapper.PostMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
  private final PostMapper postMapper;

  public PostResponse createPost(PostCreateRequest request) {
    Post post = Post.builder()
        .uesr_id(request.user_id())
        .title(request.title())
        .content(request.content())
        .build();

    // INSERT이전 : id 존재x
    // 제약 조건 위배를 대비한 코드
    postMapper.save(post);
    // userId + title + content 추가로 INSERT 쿼리 실행 시 MyBatis가 채운 id값이 함께 존재함
    // INSERT이후 : id 존재o -> useGeneratedKeys 때문에 PK 생성 -> createdAt 제외한 모든 값 리턴 가능
    // createdAt을 꼭 리턴하고 싶다면 SELECT를 다시 하는 수 밖에 없다.

    return findById(post.getId());
  }

  public PostResponse findById(Long id) {

    return null;
  }
}
