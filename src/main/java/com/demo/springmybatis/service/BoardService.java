package com.demo.springmybatis.service;

import com.demo.springmybatis.common.domain.PageRequest;
import com.demo.springmybatis.domain.Board;

import java.util.List;

public interface BoardService {

  public void register(Board board) throws Exception;

  public Board read(Integer boardNo) throws Exception;

  public void modify(Board board) throws Exception;

  public void remove(Integer boardNo) throws Exception;

  public List<Board> list(PageRequest pageRequest) throws Exception;

  public int count(PageRequest pageRequest) throws Exception;
}
