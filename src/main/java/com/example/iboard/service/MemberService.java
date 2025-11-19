package com.example.iboard.service;

import com.example.iboard.dao.*;
import com.example.iboard.dto.*;
import com.example.iboard.entity.*;
import com.example.iboard.exception.*;
import org.apache.commons.lang3.*;
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
    // 1. 비밀번호 암호화
    String encodedPassword = encoder.encode(dto.getPassword());
    // 2. 프사를 업로드했으면 인코딩, 업로드하지 않았으면 기본 프사를 저장
    MultipartFile profile = dto.getProfile();
    boolean 프사를_업로드_했는가 = profile!=null && !profile.isEmpty();
    String base64Image = "";
    try {
      if(프사를_업로드_했는가) {
        base64Image = ProfileUtil.getBase64Profile(profile);
      } else {
        base64Image = ProfileUtil.getDefaultBase64Profile();
      }
    } catch(IOException e) {
      // 처리 중 오류가 발생했다면 기본 프사로 가입 처리
      base64Image = ProfileUtil.getDefaultBase64Profile();
    }

    // 3. 암호화된 비밀번호, base64이미지를 가지고 dto를 member로 변환
    Member member = dto.toEntity(encodedPassword, base64Image);
    memberDao.save(member);
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
