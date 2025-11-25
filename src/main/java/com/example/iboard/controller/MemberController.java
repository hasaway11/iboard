package com.example.iboard.controller;

import com.example.iboard.dto.*;
import com.example.iboard.entity.*;
import com.example.iboard.service.*;
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
  @PostMapping("/api/members/new")
  public ResponseEntity<Member> signup(@ModelAttribute @Valid MemberDto.Create dto) {
    Member member = service.signup(dto);
    return ResponseEntity.ok(member);
  }

  @PreAuthorize("isAnonymous()")
  @GetMapping("/api/members/username")
  public ResponseEntity<String> searchUsername(@RequestParam @NotEmpty(message="이메일은 필수입력입니다") @Email(message="이메일을 입력하세요") String email) {
    Optional<String> result = service.findUseraname(email);
    return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).body("사용자를 찾을 수 없습니다"));
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/api/members/member")
  public ResponseEntity<MemberDto.Read> read(Principal principal) {
    String loginId = principal==null? "anonymous" : principal.getName();
    System.out.println("============================================");
    System.out.println(loginId);
    MemberDto.Read dto = service.read(principal.getName());
    return ResponseEntity.ok(dto);
  }

  @PreAuthorize("isAuthenticated()")
  @PatchMapping("/api/members/password")
  public ResponseEntity<String> changePassword(@ModelAttribute @Valid MemberDto.PasswordChange dto, Principal principal, HttpSession session) {
    boolean result = service.changePassword(dto, principal.getName());
    if(result) {
      session.invalidate();
      return ResponseEntity.ok("비밀번호 변경");
    }
    return ResponseEntity.status(409).body("비밀번호 변경 실패");
  }

  @PreAuthorize("isAuthenticated()")
  @DeleteMapping("/api/members/member")
  public ResponseEntity<String> delete(Principal principal, HttpSession session) {
    service.delete(principal.getName());
    session.invalidate();
    return ResponseEntity.ok("회원 탈퇴");
  }
}
