package com.demo.springmybatis.service;

import com.demo.springmybatis.domain.Item;

import java.util.List;

public interface ItemService {

  public void register(Item item) throws Exception;

  public Item read(Integer itemId) throws Exception;

  public void modify(Item item) throws Exception;

  public void remove(Integer itemId) throws Exception;

  public List<Item> list() throws Exception;

  public String getPicture(Integer itemId) throws Exception;

  public String getPreview(Integer itemId) throws Exception;
}
