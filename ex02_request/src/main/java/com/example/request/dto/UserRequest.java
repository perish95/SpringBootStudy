package com.example.request.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
// @AllArgsConstructor
public class UserRequest {
  private String name;
  private int age;
}
