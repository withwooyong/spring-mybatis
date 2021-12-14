package com.demo.springmybatis.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CodeDetail implements Serializable {

  private static final long serialVersionUID = 1L;

  private String classCode;
  private String codeValue;
  private String codeName;
  private int sortSeq;
  private String useYn;
  private LocalDateTime regDate;
  private LocalDateTime updDate;
}
