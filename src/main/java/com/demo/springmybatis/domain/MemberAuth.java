package com.demo.springmybatis.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class MemberAuth implements Serializable {

  private static final long serialVersionUID = 1L;

  private int userNo;
  private String auth;
}
