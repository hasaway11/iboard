package com.example.iboard.security;

import com.example.iboard.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.web.authentication.*;
import org.springframework.stereotype.*;

import java.io.*;

// 로그인 실패 시 응답 (스프링 시큐리티는 기본적으로 MVC 처리. REST로 응답을 변경)
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
    if(exception instanceof LockedException)
      ResponseUtil.sendJsonResponse(response, 403, "비활성화된 계정입니다");
    else
      ResponseUtil.sendJsonResponse(response, 401, "아이디나 비밀번호를 확인하세요");
  }
}
