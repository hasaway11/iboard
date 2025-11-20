package com.example.iboard.dao;

import com.example.iboard.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface MemberDao {
  @Select("select count(*) from member where username=#{username} and rownum=1")
  boolean existsByUsername(String username);

  long insert(Member member);

  @Select("select username from member where email=#{email} and rownum=1")
  Optional<String> findUsernameByEmail(String email);

  @Update("update member set password=#{newPassword} where username=#{username}")
  long updatePassword(String username, String newPassword);

  @Select("select * from member where username=#{username}")
  Optional<Member> findByUsername(String username);

  @Delete("delete from member where username=#{username}")
  long delete(String username);
}








