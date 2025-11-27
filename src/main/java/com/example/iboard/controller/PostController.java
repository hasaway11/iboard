package com.example.iboard.controller;

import com.example.iboard.dto.*;
import com.example.iboard.entity.*;
import com.example.iboard.service.*;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.access.annotation.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.security.*;

@RestController
public class PostController {
  @Autowired
  private PostService service;

  // 페이징
  @GetMapping("/api/posts")
  public ResponseEntity<PostDto.Pages> findAll(@RequestParam(defaultValue="1") int pageno, @RequestParam(defaultValue="10") int pagesize) {
    return ResponseEntity.ok(service.findAll(pageno, pagesize));
  }

  // 글 읽기
  @Validated
  @GetMapping("/api/posts/post")
  public ResponseEntity<PostDto.Read> findByPno(@RequestParam(required=false) @NotNull(message="글번호는 필수입력입니다") Integer pno, @RequestParam(defaultValue="1") boolean includeComments, Principal principal) {
    String loginId = principal==null? null : principal.getName();
    return ResponseEntity.ok(service.findByPno(pno, includeComments, loginId));
  }

  @Secured("ROLE_USER")
  @PostMapping("/api/posts/new")
  public ResponseEntity<Post> write(@ModelAttribute @Valid PostDto.Write dto, Principal principal) {
    Post post = service.write(dto, principal.getName());
    return ResponseEntity.ok(post);
  }

  @Secured("ROLE_USER")
  @PutMapping("/api/posts/post")
  public ResponseEntity<String> update(@ModelAttribute @Valid PostDto.Update dto, Principal principal) {
    service.update(dto, principal.getName());
    return ResponseEntity.ok("글을 변경했습니다");
  }

  @Validated
  @Secured("ROLE_USER")
  @DeleteMapping("/api/posts/post")
  public ResponseEntity<String> delete(@RequestParam(required=false) @NotNull(message="글번호는 필수입력입니다") Integer pno, Principal principal) {
    service.delete(pno, principal.getName());
    return ResponseEntity.ok("글을 삭제했습니다");
  }
}
