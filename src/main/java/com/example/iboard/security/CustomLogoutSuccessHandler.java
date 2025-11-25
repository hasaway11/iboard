package com.example.iboard.security;

import com.example.iboard.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.core.*;
import org.springframework.security.web.authentication.logout.*;
import org.springframework.stereotype.*;

import java.io.*;

// 로그아웃 처리 (스프링 시큐리티는 기본적으로 MVC 처리. REST로 응답을 변경)
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    System.out.println("로그아웃 성공");
    ResponseUtil.sendJsonResponse(response, 200, "로그아웃 성공");
  }
}
