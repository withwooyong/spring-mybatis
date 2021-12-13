package com.demo.springmybatis.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class CodeClass implements Serializable {

  private static final long serialVersionUID = 1L;

  private String classCode;
  private String className;
  private String useYn;
  private Date regDate;
  private Date updDate;
}
