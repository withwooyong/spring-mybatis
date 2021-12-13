package com.demo.springmybatis.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class ChargeCoin implements Serializable {

  private static final long serialVersionUID = 1L;

  private int historyNo;
  private int userNo;
  private int amount;
  private Date regDate;
}
