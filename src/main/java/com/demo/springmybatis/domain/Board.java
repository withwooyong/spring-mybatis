package com.demo.springmybatis.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Board implements Serializable {

  private static final long serialVersionUID = 1L;

  private int boardNo;
  private String title;
  private String content;
  private String writer;
  private LocalDateTime regDate;
}
