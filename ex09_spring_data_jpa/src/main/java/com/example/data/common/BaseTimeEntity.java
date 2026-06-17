package com.example.data.common;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass // 자식 엔티티에 의해서 매핑되는 부모 클래스임을 알림
@EntityListeners(AuditingEntityListener.class) // 엔티티의 상태 변화(생성, 변경 등)를 감지하여 날짜를 자동으로 입력
public abstract class BaseTimeEntity {

  @CreatedDate // 엔티티 생성 시간을 자동으로 저장
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate // 엔티티 값이 변경된 시간을 자동으로 저장
  private LocalDateTime updatedAt;
}
