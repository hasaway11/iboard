package com.example.iboard.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.time.*;

//  @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss") : JSON 출력 형식을 지정하는 어노테이션

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
  private long cno;
  private String content;
  private String writer;
  @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
  private LocalDateTime writeTime;
  private long pno;
}

