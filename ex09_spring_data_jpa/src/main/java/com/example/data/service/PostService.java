package com.example.data.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.data.domain.Comment;
import com.example.data.domain.Post;
import com.example.data.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 읽기 전용 트랜잭션
public class PostService {

  private final PostRepository postRepository;

  // 생성
  @Transactional // 읽기 전용 아님
  public Long createPost(String title, String content) {
    Post post = new Post(title, content);
    return postRepository.save(post).getId();
  }

  // 단 건 조회
  public Post getPost(Long id) {
    Post post = postRepository.findPostWithComments(id);
    return Optional.ofNullable(post)
        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다.")); // 조회 결과 없으면 예외 발생 추가
  }

  // 목록 조회 (페이징, 제목 키워드 포함)
  public Page<Post> getPosts(String keyword, Pageable pageable) {
    if (keyword != null && !keyword.isBlank()) {
      return postRepository.findByTitleContaining(keyword, pageable);
    }
    return postRepository.findAll(pageable);
  }

  // 수정 (조회 후(조회 결과가 영속화 됨) 엔티티 수정(변경 감지로 인해 UPDATE 자동 생성))
  @Transactional // 읽기 전용 아님
  public void updatePost(Long id, String title, String content) {
    Post findPost = postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
    findPost.updatePost(title, content);
  }

  // 삭제 (수정과 동일하게, 조회 후 삭제)
  @Transactional // 읽기 전용 아님
  public void deletePost(Long id) {
    Post findPost = postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
    postRepository.delete(findPost);
  }

  // 댓글 등록
  @Transactional // 읽기 전용 아님
  public void addComment(Long postId, String content) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
    Comment comment = new Comment(content);
    post.addComment(comment);
  }
}
