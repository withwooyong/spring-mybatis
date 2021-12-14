package com.demo.springmybatis.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class Member implements Serializable {

  private static final long serialVersionUID = 1L;

  private int userNo;

  @NotBlank
  private String userId;

  @NotBlank
  private String userPw;

  @NotBlank
  private String userName;

  private String job;
  private int coin;

  private boolean enabled;

  private LocalDateTime regDate;
  private LocalDateTime updDate;

  private List<MemberAuth> authList;
}
