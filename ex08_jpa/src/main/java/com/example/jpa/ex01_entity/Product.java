package com.example.jpa.ex01_entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;

@Table(name = "products", uniqueConstraints = {
    @UniqueConstraint(name = "UC_PRODUCT_CODE", columnNames = { "product_code" })
})
@Entity
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "product_code", nullable = false, length = 20)
  private String productCode;

  @Column(name = "product_name", nullable = false, length = 100)
  private String name;

  @Column(nullable = false)
  private Integer price;

  @Column(name = "register_at", updatable = false)
  private LocalDateTime registerAt;

  @Lob // varchar 벗어나는 길이 처리 가능
  private String description;

  @Transient
  private String tempSessionId;

  // JPA 스펙상 기본 생성자는 필수 요소임(public 권한 축소해서 protected 권한 사용 권장)
  public Product() {
  }

  public Product(String productCode, String name, Integer price, String description) {
    this.productCode = productCode;
    this.name = name;
    this.price = price;
    this.description = description;
    this.registerAt = LocalDateTime.now();
  }

  public Long getId() {
    return id;
  }

  public String getProductCode() {
    return productCode;
  }

  public String getName() {
    return name;
  }

  public Integer getPrice() {
    return price;
  }

  public LocalDateTime getRegisterAt() {
    return registerAt;
  }

  public String getDescription() {
    return description;
  }

  public String getTempSessionId() {
    return tempSessionId;
  }

  // JPA 엔티티 설계시 @ToString 사용을 권장하지 않고 수동을 권장한다. ->상호 참조 문제로 인해 무한루프 발생 가능성 존재
  @Override
  public String toString() {
    return "Product [id=" + id + ", productCode=" + productCode + ", name=" + name + ", price=" + price
        + ", registerAt=" + registerAt + ", description=" + description + ", tempSessionId=" + tempSessionId + "]";
  }

}
