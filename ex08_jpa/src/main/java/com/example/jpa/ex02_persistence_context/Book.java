package com.example.jpa.ex02_persistence_context;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Book {
  // UUID: 전역 식별자
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String title;

  private String author;

  @Override
  public String toString() {
    return "Book [id=" + id + ", title=" + title + ", author=" + author + "]";
  }

  public Book(String title, String author) {
    this.title = title;
    this.author = author;
  }

  // 책 이름을 바꿔주는 비즈니스 메서드
  // 사실상 setter지만 수정 가능성이 있는 컬럼만 바꾸고
  // 시그니처도 관습대로 만들지 않도록 한다.
  public void changeTitle(String title) {
    this.title = title;
  }

}
