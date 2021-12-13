package com.demo.springmybatis.domain;

import com.demo.springmybatis.common.domain.Pagination;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class Page implements Serializable {

  private static final long serialVersionUID = 1L;

  private List<Board> list;
  private Pagination pagination;
}
