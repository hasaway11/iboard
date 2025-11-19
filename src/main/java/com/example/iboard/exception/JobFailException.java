package com.example.iboard.exception;

import lombok.*;

@AllArgsConstructor
@Getter
public class JobFailException extends RuntimeException {
  private String message;
}
