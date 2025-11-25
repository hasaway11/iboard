package com.example.iboard.dao;

import com.example.iboard.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface PostDao {
  long insert(Post post);

  List<Post> findAll(long pageno, long pagesize);

  @Select("select count(*) from post")
  long count();

  @Update("update post set read_cnt=read_cnt+1 where pno=#{pno}")
  long increaseReadCnt(long pno);

  @Select("select * from post where pno=#{pno}")
  Optional<Post> findByPno(long pno);

  @Update("update post set title=#{title}, content=#{content} where pno=#{pno}")
  long update(String title, String content, long pno);

  @Delete("delete from post where pno=#{pno}")
  long delete(long pno);
}








