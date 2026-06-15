package com.example.jpa.ex06_bidirection;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String orderNumber;

  // 양방향 연관 관계의 주인이 아니라고 명시합니다. 바로 "mappedBy" 속성을 이용합니다.
  // mappedBy 속성에는 반대편의 필드명을 그대로 작성합니다.
  // cascade = CascadeType.ALL:영속성 전이(부모만 영속화해도. 자식이 함께 영속화 됨)
  // orphanRemoval = true : 고아(리스트에는 없지만, 실제로는 존재하는 자식 엔티티)
  // 고아가 발생하면, 해당 자식 엔티티를 삭제하기 위한 DELETE문 자동 생성
  // 고아 만드는 방법: 리스트.remove(삭제 할 엔티티 번호 or 삭제 할 엔티티 자체)
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItem> orderItems = new ArrayList<>();

  public Order(String orderNumber) {
    this.orderNumber = orderNumber;
  }

  // 편의상 비즈니스 메서드
  // 비즈니스 메서드 작성 시 반대편 편의 메서드와 연동해서 만들기
  public void addOrderItem(OrderItem item) {
    this.orderItems.add(item); // Order123 주문에 iPad를 넣는다.
    if (item.getOrder() == null)
      item.setOrder(this); // iPad의 주문 번호를 Order123으로 세팅한다.
  }

}
