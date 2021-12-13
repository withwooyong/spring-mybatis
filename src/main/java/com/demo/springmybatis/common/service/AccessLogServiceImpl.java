package com.demo.springmybatis.common.service;

import com.demo.springmybatis.common.domain.AccessLog;
import com.demo.springmybatis.common.mapper.AccessLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessLogServiceImpl implements AccessLogService {

  @Autowired
  private AccessLogMapper mapper;

  @Override
  public void register(AccessLog accessLog) throws Exception {
    mapper.create(accessLog);
  }

  @Override
  public List<AccessLog> list() throws Exception {
    return mapper.list();
  }
}
