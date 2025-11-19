package com.example.iboard.dao;

import com.example.iboard.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface MemberDao {
  @Select("select count(*) from members where username=#{username} and rownum=1")
  boolean existsByUsername(String username);

  int save(Member member);

  @Select("select username from members where email=#{email} and rownum=1")
  Optional<String> findUsernameByEmail(String email);

  @Update("update members set password=#{newPassword} where username=#{username}")
  int updatePassword(String username, String newPassword);

  @Select("select * from members where username=#{username}")
  Optional<Member> findByUsername(String username);

  @Delete("delete from members where username=#{username}")
  int delete(String username);
}








