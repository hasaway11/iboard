package com.example.iboard.dao;

import com.example.iboard.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface CommentDao {
  @Insert("insert into comments(cno, content, writer, pno, write_time) values(comments_seq.nextval, #{content}, #{writer}, #{pno}, #{writeTime})")
  int save(Comment comment);

  @Select("select * from comments where pno=#{pno} order by cno desc")
  List<Comment> findByPno(long pno);

  @Delete("delete from comments where pno=#{pno}")
  long deleteByPno(long pno);

  @Delete("delete from comments where cno=#{cno} and writer=#{writer} and rownum=1")
  long deleteByCnoAndWriter(long cno, String writer);
}
