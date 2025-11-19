package com.example.iboard.util;

import com.fasterxml.jackson.databind.*;
import jakarta.servlet.http.*;

import java.io.*;

// RESG 방식의 응답은 상태코드 + 응답 데이터. 이걸 구현한 것이 스프링의 ResponseEntity
// com.example.iboard.security 패키지의 클래스들은 스프링 컨트롤러가 아니라 일반 자바 클래스여서 ResponseEntity 사용 불가
// 일반 자바 클래스에서 상태코드 + 응답 데이터를 response 객체에 담아 응답하는 메소드

public class ResponseUtil {
  // JSON 변환 객체 생성 (스프링이 아니므로 수동 생성 필요)
  private static final ObjectMapper mapper = new ObjectMapper();

  public static void sendJsonResponse(HttpServletResponse res, int statusCode, Object data) throws IOException {
    // 응답 객체에 한글 설정(안하면 한글이 깨진다)
    res.setContentType("application/json; charset=UTF-8");
    // 응답 객체에 상태 코드 설정
    res.setStatus(statusCode);
    // 결과 데이터(data)를 JSON으로 변환한 다음 응답 객체에 담는다
    res.getWriter().write(mapper.writeValueAsString(data));
  }
}
