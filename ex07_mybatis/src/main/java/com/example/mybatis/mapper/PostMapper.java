package com.example.mybatis.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.mybatis.domain.Post;

@Mapper
public interface PostMapper {
  long countAll();

  Optional<Post> findById(long id);

  List<Post> findAll(@Param("offset") long offset, @Param("size") int size, @Param("sort") String sort);

  int save(Post post);

  int update(Post post);

  int deleteById(long id);
}
