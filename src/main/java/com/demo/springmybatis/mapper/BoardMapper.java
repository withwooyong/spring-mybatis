package com.demo.springmybatis.mapper;

import com.demo.springmybatis.common.domain.PageRequest;
import com.demo.springmybatis.domain.Board;

import java.util.List;

public interface BoardMapper {

  public void create(Board board) throws Exception;

  public Board read(Integer boardNo) throws Exception;

  public void update(Board board) throws Exception;

  public void delete(Integer boardNo) throws Exception;

  public List<Board> list(PageRequest pageRequest) throws Exception;

  public int count(PageRequest pageRequest) throws Exception;
}
