package com.demo.springmybatis.mapper;

import com.demo.springmybatis.common.domain.CodeLabelValue;

import java.util.List;

public interface CodeMapper {

  public List<CodeLabelValue> getCodeClassList() throws Exception;

  public List<CodeLabelValue> getCodeList(String classCode) throws Exception;
}
