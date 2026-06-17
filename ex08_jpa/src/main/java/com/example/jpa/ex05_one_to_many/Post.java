package com.example.jpa.ex05_one_to_many;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

//부모 엔티티
@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String title;

  @OneToMany
  // Cascade = CascadeType.PERSIST: Post 영속화 시 연관 관계를 가진 PostComment를 함께 영속화
  @JoinColumn(name = "post_id") // Post가 아닌 PostComment 테이블(자식 테이블)에 생성할 FK칼럼명 작성
  private List<PostComment> comments = new ArrayList<>();

  public Post(String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    return "Post [id=" + id + ", title=" + title + ", comments=" + comments + "]";
  }

  // 비지니스 편의상 만든 메서드
  public void addComment(PostComment comment) {
    this.comments.add(comment);
  }

}
