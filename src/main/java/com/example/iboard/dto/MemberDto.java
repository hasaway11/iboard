package com.example.iboard.dto;

import com.example.demo.entity.*;
import com.example.demo.util.validation.*;
import com.example.iboard.entity.*;
import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.*;

import java.time.*;

public class MemberDto {
  @Data
  public static class Create {
    @Pattern(regexp = "^[a-z0-9]{6,10}$")
    @NotEmpty(message="아이디는 필수입력입니다")
    private String username;

    @Pattern(regexp = "^[A-Za-z0-9]{6,10}$")
    @NotEmpty(message="비밀번호는 필수입력입니다")
    private String password;

    @Email
    @NotEmpty(message="이메일은 필수입력입니다")
    private String email;
    private MultipartFile profile;

    public Member toEntity(String encodedPassword, String base64Image) {
      return new Member(username, encodedPassword, email, base64Image);
    }
  }

  @Data
  @AllArgsConstructor
  public static class Read {
    private String username;
    private String email;
    private String profile;
    @JsonFormat(pattern="yyyy년 MM월 dd일")
    private LocalDate joinday;
    // 가입기간
    private long days;
  }

  @Data
  public static class PasswordChange {
    @Pattern(regexp = "^[a-z0-9]{6,10}$")
    @NotEmpty(message="기존 비밀번호는 필수입력입니다")
    private String currentPassword;

    @Pattern(regexp = "^[a-z0-9]{6,10}$")
    @NotEmpty(message="새 비밀번호는 필수입력입니다")
    private String newPassword;
  }

}










