package com.demo.springmybatis.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class UserItem implements Serializable {

  private static final long serialVersionUID = 1L;

  private int userItemNo;
  private int userNo;

  private int itemId;
  private String itemName;
  private Integer price;
  private String description;
  private String pictureUrl;

  private Date regDate;
}
