package com.example.iboard.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.service.*;
import com.example.iboard.entity.*;
import com.example.iboard.service.*;
import io.swagger.v3.oas.annotations.*;
import jakarta.servlet.http.*;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import java.security.*;
import java.util.*;

@Controller
public class MemberController {
  @Autowired
  private MemberService service;

  @PreAuthorize("isAnonymous()")
  @Operation(summary= "아이디 사용여부 확인", description="아이디가 사용가능한 지 확인")
  @GetMapping("/api/members/check-username")
  public ResponseEntity<String> checkUsername(@RequestParam @NotEmpty(message="아이디를 입력하세요") String username) {
    // 소문자로 변환
    username = username.toLowerCase();
    boolean result = service.checkUsername(username);
    if(result)
      return ResponseEntity.ok("사용가능합니다");
    return ResponseEntity.status(HttpStatus.CONFLICT).body("사용중인 아이디입니다");
  }

  @PreAuthorize("isAnonymous()")
  @Operation(summary="회원가입", description="회원가입 및 프로필 사진 업로드")
  @PostMapping("/api/members/new")
  public ResponseEntity<Member> signup(@ModelAttribute @Valid MemberDto.Create dto) {
    Member member = service.signup(dto);
    return ResponseEntity.ok(member);
  }

  @PreAuthorize("isAnonymous()")
  @Operation(summary="아이디 찾기", description="가입한 이메일로 아이디를 찾는다")
  @GetMapping("/api/members/username")
  public ResponseEntity<String> searchUsername(@RequestParam @NotEmpty(message="이메일은 필수입력입니다") @Email(message="이메일을 입력하세요") String email) {
    Optional<String> result = service.searchUseraname(email);
    return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).body("사용자를 찾을 수 없습니다"));
  }

  @PreAuthorize("isAuthenticated()")
  @Operation(summary = "내 정보 보기", description = "내 정보 보기")
  @GetMapping("/api/members/member")
  public ResponseEntity<MemberDto.Read> read(Principal principal) {
    MemberDto.Read dto = service.read(principal.getName());
    return ResponseEntity.ok(dto);
  }

  @PreAuthorize("isAuthenticated()")
  @Operation(summary = "비밀번호 변경", description = "기존 비밀번호, 새 비밀번호로 비밀번호 변경")
  @PatchMapping("/api/members/password")
  public ResponseEntity<String> changePassword(@ModelAttribute @Valid MemberDto.PasswordChange dto, Principal principal) {
    boolean result = service.changePassword(dto, principal.getName());
    if(result)
      return ResponseEntity.ok("비밀번호 변경");
    return ResponseEntity.status(409).body("비밀번호 변경 실패");
  }

  @PreAuthorize("isAuthenticated()")
  @Operation(summary = "회원 탈퇴", description = "로그아웃시킨 후 회원 탈퇴")
  @DeleteMapping("/api/members/member")
  public ResponseEntity<String> resign(Principal principal, HttpSession session) {
    service.resign(principal.getName());
    session.invalidate();
    return ResponseEntity.ok("회원 탈퇴");
  }
}
