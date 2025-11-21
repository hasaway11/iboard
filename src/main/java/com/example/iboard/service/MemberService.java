package com.example.iboard.service;

import com.example.iboard.dao.*;
import com.example.iboard.dto.*;
import com.example.iboard.entity.*;
import com.example.iboard.exception.*;
import com.example.iboard.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.*;

import java.io.*;
import java.util.*;

@Service
public class MemberService {
  @Autowired
  private MemberDao memberDao;
  @Autowired
  private PasswordEncoder encoder;

  public boolean checkUsername(String username) {
    return !memberDao.existsByUsername(username);
  }

  public Member signup(MemberDto.Create dto) {
    if(memberDao.existsByUsername(dto.getUsername()))
      throw new JobFailException("사용중인 아이디입니다");

    String encodedPassword = encoder.encode(dto.getPassword());
    Member member = dto.toEntity(encodedPassword);
    memberDao.insert(member);
    return member;
  }

  public Optional<String> findUseraname(String email) {
    return memberDao.findUsernameByEmail(email);
  }

  public MemberDto.Read read(String loginId) {
    Member member  = memberDao.findByUsername(loginId).orElseThrow(()->new JobFailException("사용자를 찾을 수 없습니다"));
    return member.toRead();
  }

  public boolean changePassword(MemberDto.PasswordChange dto, String loginId) {
    // 기존 암호화된 비밀번호를 읽어와 비밀번호가 맞는 지 확인 -> 틀리면 false
    Member member  = memberDao.findByUsername(loginId).orElseThrow(()->new JobFailException("사용자를 찾을 수 없습니다"));
    if(!encoder.matches(dto.getCurrentPassword(), member.getPassword()))
      return false;
    // 비밀번호가 일치한 경우 새 비밀번호로 업데이트
    return memberDao.updatePassword(loginId, encoder.encode(dto.getNewPassword()))==1;
  }

  public void delete(String loginId) {
    memberDao.delete(loginId);
  }
}
