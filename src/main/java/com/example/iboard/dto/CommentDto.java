package com.example.iboard.dto;

import com.example.iboard.entity.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentDto {
  @Data
  public static class Craete {
    @NotNull(message="글번호가 없습니다")
    private Long pno;
    @NotEmpty(message="내용을 입력하세요")
    private String content;

    public Comment toEntity(String loginId) {
      return new Comment(0, content, loginId, LocalDateTime.now(), pno);
    }
  }

  @Data
  public static class Delete {
    @NotNull(message="댓글 번호가 없습니다")
    private Long cno;
    @NotNull(message="글번호가 없습니다")
    private Long pno;
  }
}