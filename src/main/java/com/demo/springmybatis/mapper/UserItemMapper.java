package com.demo.springmybatis.mapper;

import com.demo.springmybatis.domain.UserItem;

import java.util.List;

public interface UserItemMapper {

	public void create(UserItem userItem) throws Exception;

	public UserItem read(Integer userItemNo) throws Exception;

	public List<UserItem> listAll() throws Exception;

	public List<UserItem> list(Integer userNo) throws Exception;

}
