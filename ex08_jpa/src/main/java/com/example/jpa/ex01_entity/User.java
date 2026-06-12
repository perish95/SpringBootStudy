package com.example.jpa.ex01_entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {
  @Id
  @TableGenerator(name = "userIdGenerator", table = "user_id_seq", pkColumnName = "entity", pkColumnValue = "User", valueColumnName = "nextval", initialValue = 0, allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "userIdGenerator")
  private Long id;

  @Column(nullable = false, length = 100)
  private String username;

  @Column(nullable = false, unique = true, length = 100)
  private String email;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Enumerated(EnumType.STRING)
  @Column(length = 5)
  private Role role;

  public User(String username, String email, Role role) {
    this.username = username;
    this.email = email;
    this.role = role;
  }
}

/*
 * User 엔티티는 키값을 알기 때문에 INSERT가 실행되지 않음
 * 왜냐하면 테이블을 만들엇기때문
 * 원래는 persist하면 자동으로 INSERT가 됨 -> PK를 모르기 때문에
 */