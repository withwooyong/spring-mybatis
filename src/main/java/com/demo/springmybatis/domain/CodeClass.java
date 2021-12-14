package com.demo.springmybatis.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CodeClass implements Serializable {

  private static final long serialVersionUID = 1L;

  private String classCode;
  private String className;
  private String useYn;
  private LocalDateTime regDate;
  private LocalDateTime updDate;
}
