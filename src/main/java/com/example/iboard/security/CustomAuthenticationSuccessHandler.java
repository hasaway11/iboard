package com.example.iboard.security;

import com.example.iboard.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.security.web.authentication.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.util.*;

// 로그인 성공 응답 (스프링 시큐리티는 기본적으로 MVC 처리. REST로 응답을 변경)
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    String username = authentication.getName();
    String role = authentication.getAuthorities().stream().findFirst().get().getAuthority();
    ResponseUtil.sendJsonResponse(response, 200, Map.of("username", username, "role", role));
  }
}








