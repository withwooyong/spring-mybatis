package com.demo.springmybatis.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class Board implements Serializable {

  private static final long serialVersionUID = 1L;

  private int boardNo;
  private String title;
  private String content;
  private String writer;
  private Date regDate;
}
