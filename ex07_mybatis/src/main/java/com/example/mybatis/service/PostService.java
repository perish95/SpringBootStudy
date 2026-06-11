package com.example.mybatis.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.mybatis.domain.Post;
import com.example.mybatis.dto.PageResponse;
import com.example.mybatis.dto.PostCreateRequest;
import com.example.mybatis.dto.PostResponse;
import com.example.mybatis.dto.PostUpdateRequest;
import com.example.mybatis.exception.CustomException;
import com.example.mybatis.exception.ErrorCode;
import com.example.mybatis.mapper.PostMapper;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true, rollbackFor = Exception.class) // select이외의 메소드 걸어주기
@Service
@RequiredArgsConstructor
public class PostService {
  private final PostMapper postMapper;

  @Transactional
  public PostResponse createPost(PostCreateRequest request) {
    Post post = Post.builder()
        .userId(request.userId())
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
    Post post = postMapper
        .findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    return PostResponse.from(post); // Post로부터 PostResponse 얻기(실무 코드, 정적 메서드 패턴:from)
  }

  public PageResponse<PostResponse> getPosts(int page, int size, String sort) {
    long offset = (page - 1) * size;
    long totalElements = postMapper.countAll();
    int totalPages = (int) Math.ceil((double) totalElements / size);

    List<Post> posts = postMapper.findAll(offset, size, sort);
    List<PostResponse> contents = posts.stream()
        .map(PostResponse::from) // .map(post -> PostResponse.from(post))
        .collect(Collectors.toList());

    return new PageResponse<>(contents, page, size, totalPages, totalElements, sort);
  }

  @Transactional
  public PostResponse updatePost(Long id, @RequestBody PostUpdateRequest request) {
    Post post = postMapper
        .findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    Post updatedPost = Post.builder()
        .id(request.id())
        .userId(id)
        .title(request.title())
        .content(request.content())
        .build();

    postMapper.update(updatedPost);

    return PostResponse.from(updatedPost);
  }

  @Transactional
  public void deletePost(Long id) {
    postMapper.deleteById(id);
  }
}
