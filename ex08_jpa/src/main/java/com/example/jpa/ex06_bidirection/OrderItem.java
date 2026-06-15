package com.example.jpa.ex06_bidirection;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "order_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class OrderItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String itemName;
  private Integer count;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id") // OrderItem 테이블에 생성될 roder_id 칼럼을 외래키로 지정
  // 외래키를 가진 자식이 항상 양방향 연관 관계의 주인이 된다.
  private Order order;

  public OrderItem(String itemName, Integer count) {
    this.itemName = itemName;
    this.count = count;
  }

  /*
   * 예시
   * Order order = new Order("order1234");
   * OrderItem item1 = new OrderItem("iPad",1);
   * item1.setOrder(order);
   */
  // 비즈니스 메서드 작성 시 반대편 편의 메서드와 연동해서 만들기
  public void setOrder(Order order) {
    this.order = order;
    if (!order.getOrderItems().contains(this))
      order.getOrderItems().add(this);
  }
}
