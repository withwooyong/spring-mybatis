package com.demo.springmybatis.common.service;

import com.demo.springmybatis.common.domain.AccessLog;

import java.util.List;

public interface AccessLogService {

  public void register(AccessLog accessLog) throws Exception;

  public List<AccessLog> list() throws Exception;
}
