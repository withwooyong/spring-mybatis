package com.demo.springmybatis.common.mapper;

import com.demo.springmybatis.common.domain.AccessLog;

import java.util.List;

public interface AccessLogMapper {

  public void create(AccessLog accessLog) throws Exception;
  public List<AccessLog> list() throws Exception;
}
