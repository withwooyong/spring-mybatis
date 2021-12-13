package com.demo.springmybatis.service;

import com.demo.springmybatis.common.domain.CodeLabelValue;

import java.util.List;

public interface CodeService {

  public List<CodeLabelValue> getCodeClassList() throws Exception;

  public List<CodeLabelValue> getCodeList(String classCode) throws Exception;
}
