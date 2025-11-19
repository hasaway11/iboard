package com.example.iboard.controller;

import io.swagger.v3.oas.annotations.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@Controller
public class AuthController {
	@Operation(summary="로그인 여부 확인", description="로그인했으면 아이디와 권한을 응답")
	@GetMapping(path="/api/auth/check")
	public ResponseEntity<Map<String, String>> checkLogin(Authentication authentication) {
		if(authentication!=null) {
			String username = authentication.getName();
			String role = authentication.getAuthorities().stream().findFirst().get().getAuthority();
			return ResponseEntity.ok(Map.of("username", username, "role", role));
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	}
}
