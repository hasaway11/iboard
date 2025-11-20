package com.example.iboard.advice;

import com.example.iboard.exception.*;
import jakarta.validation.*;
import org.springframework.http.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class ProcessingFaultAdvice {
  // @RequestParam 파라미터를 전달하지 않은 경우(null)
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<String> missingServletRequestParameterException(MissingServletRequestParameterException e) {
    String paramName = e.getParameterName();
    return ResponseEntity.status(409).body(paramName + ": 필수입력입니다");
  }

  // @RequestParam 파라미터 검증 실패에 대한 처리 (username= 과 같은 경우)
  @ExceptionHandler(HandlerMethodValidationException.class)
  public ResponseEntity<String> handlerMethodValidationException(HandlerMethodValidationException e) {
    String message = e.getAllErrors().get(0).getDefaultMessage();
    return ResponseEntity.status(409).body(message);
  }

  // @Valid 검증 실패에 대한 처리
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {
    // DTO를 생성 과정에서 발생했을 오류들 중 하나만 리턴
    return ResponseEntity.status(409).body(e.getAllErrors().get(0).getDefaultMessage());
  }

  // 사용자 정의 : 작업이 실패했을 때
  @ExceptionHandler(JobFailException.class)
  public ResponseEntity<String> jobFailException(JobFailException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
  }
}
