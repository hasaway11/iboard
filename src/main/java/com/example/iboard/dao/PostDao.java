package com.example.iboard.dao;

import com.example.iboard.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface PostDao {
  int save(Post post);

  List<Post> findAll(long pageno, long pagesize);

  @Select("select count(*) from post")
  int count();

  @Update("update post set read_cnt=read_cnt+1 where pno=#{pno}")
  int increaseReadCnt(long pno);

  @Select("select * from post where pno=#{pno}")
  Optional<Post> findByPno(long pno);

  @Update("update posts set title=#{title}, content=#{content} where pno=#{pno}")
  int update(String title, String content, long pno);

  @Delete("delete from posts where pno=#{pno}")
  int delete(long pno);
}








