package com.example.iboard.security;

import com.example.iboard.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.core.*;
import org.springframework.security.web.*;
import org.springframework.stereotype.*;

import java.io.*;

// 401 응답 (스프링 시큐리티는 기본적으로 MVC 처리. REST로 응답을 변경)
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    ResponseUtil.sendJsonResponse(response, 401, "로그인이 필요합니다");
  }
}
