package com.example.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper // 나는 xml Mapper를 호출할 때 사용하는 인터페이스
public interface UserMapper {
  long countAll(); // XML Mapper에서 id="countAll"인 쿼리 실행하기
  
}
