package com.demo.springmybatis.common.service;

import com.demo.springmybatis.common.domain.PerformanceLog;
import com.demo.springmybatis.common.mapper.PerformanceLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceLogServiceImpl implements PerformanceLogService {

  @Autowired
  private PerformanceLogMapper mapper;

  @Override
  public void register(PerformanceLog performanceLog) throws Exception {
    mapper.create(performanceLog);
  }

  @Override
  public List<PerformanceLog> list() throws Exception {
    return mapper.list();
  }
}
