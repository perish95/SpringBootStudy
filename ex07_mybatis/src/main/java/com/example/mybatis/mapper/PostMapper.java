package com.example.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.mybatis.domain.Post;

@Mapper
public interface PostMapper {
  long countAll();

  Post findById(long id);

  List<Post> findAll(@Param("offset") long offset, @Param("size") int size);

  int save(Post post);

  int update(Post post);

  int deleteById(long id);
}
