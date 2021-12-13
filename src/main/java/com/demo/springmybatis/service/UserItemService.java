package com.demo.springmybatis.service;

import com.demo.springmybatis.domain.Item;
import com.demo.springmybatis.domain.Member;
import com.demo.springmybatis.domain.UserItem;

import java.util.List;

public interface UserItemService {

  public void register(Member member, Item item) throws Exception;

  public UserItem read(Integer userItemNo) throws Exception;

  public List<UserItem> listAll() throws Exception;

  public List<UserItem> list(Integer userNo) throws Exception;
}
