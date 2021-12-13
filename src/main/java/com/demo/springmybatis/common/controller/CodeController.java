package com.demo.springmybatis.common.controller;

import com.demo.springmybatis.common.domain.CodeLabelValue;
import com.demo.springmybatis.service.CodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/codes")
public class CodeController {

  private static final Logger logger = LoggerFactory.getLogger(CodeController.class);

  @Autowired
  private CodeService codeService;

  @RequestMapping(value = "/codeClass", method = RequestMethod.GET)
  public ResponseEntity<List<CodeLabelValue>> codeClassList() throws Exception {
    logger.info("codeClassList");

    return new ResponseEntity<>(codeService.getCodeClassList(), HttpStatus.OK);
  }

  @RequestMapping(value = "/job", method = RequestMethod.GET)
  public ResponseEntity<List<CodeLabelValue>> jobList() throws Exception {
    logger.info("jobList");

    String classCode = "A01";
    List<CodeLabelValue> jobList = codeService.getCodeList(classCode);

    return new ResponseEntity<>(jobList, HttpStatus.OK);
  }

  @RequestMapping(value = "/searchType", method = RequestMethod.GET)
  public ResponseEntity<List<CodeLabelValue>> searchTypeList() throws Exception {
    logger.info("searchTypeList");

    List<CodeLabelValue> searchTypeCodeValueList = new ArrayList<CodeLabelValue>();
    searchTypeCodeValueList.add(new CodeLabelValue("n", "---"));
    searchTypeCodeValueList.add(new CodeLabelValue("t", "Title"));
    searchTypeCodeValueList.add(new CodeLabelValue("c", "Content"));
    searchTypeCodeValueList.add(new CodeLabelValue("w", "Writer"));
    searchTypeCodeValueList.add(new CodeLabelValue("tc", "Title OR Content"));
    searchTypeCodeValueList.add(new CodeLabelValue("cw", "Content OR Writer"));
    searchTypeCodeValueList.add(new CodeLabelValue("tcw", "Title OR Content OR Writer"));

    return new ResponseEntity<>(searchTypeCodeValueList, HttpStatus.OK);
  }
}
