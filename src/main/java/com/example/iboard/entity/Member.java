package com.example.iboard.entity;

import com.example.iboard.dto.*;
import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.time.*;
import java.time.temporal.*;

// @JsonIgnore : JSON으로 변환할 때 생략하도록 지정

@Getter
public class Member {
  private String username;
  @JsonIgnore
  private String password;
  private String email;
  @JsonIgnore
  private String profile;
  private LocalDate joinDay = LocalDate.now();

  public Member(String username, String password, String email, String profile) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.profile = profile;
  }

  public MemberDto.Read toRead() {
    long days = ChronoUnit.DAYS.between(joinDay, LocalDate.now());
    return new MemberDto.Read(username, email, profile, joinDay, days);
  }
}
