package com.example.jpa.ex04_many_to_one;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

//부모 엔티티
@Entity
@Table(name = "categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, length = 100)
  private String name;

  public Category(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Category [id=" + id + ", name=" + name + "]";
  }

}
