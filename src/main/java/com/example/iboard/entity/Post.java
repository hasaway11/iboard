package com.example.iboard.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.time.*;

// @JsonInclude : 값이 null이 아닐 때만 JSON 출력에 포함

@Getter
public class Post {
  private long pno;
  private String title;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String content;
  private String writer;
  @JsonFormat(pattern="yyyy년 MM월 dd일 hh:mm:ss")
  private LocalDateTime writeTime = LocalDateTime.now();
  private long readCnt = 0;

  public Post(String title, String content, String writer) {
    this.title = title;
    this.content = content;
    this.writer = writer;
  }

  public void increaseReadCnt() {
    this.readCnt++;
  }
}
