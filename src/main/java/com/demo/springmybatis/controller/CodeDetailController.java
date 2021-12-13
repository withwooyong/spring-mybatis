package com.demo.springmybatis.controller;

import com.demo.springmybatis.domain.CodeDetail;
import com.demo.springmybatis.service.CodeDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/codedetails")
public class CodeDetailController {

  private static final Logger logger = LoggerFactory.getLogger(CodeDetailController.class);

  @Autowired
  private CodeDetailService codeDetailService;

  @RequestMapping(value = "/{classCode}/{codeValue}", method = RequestMethod.GET)
  public ResponseEntity<CodeDetail> read(@PathVariable("classCode") String classCode, @PathVariable("codeValue") String codeValue) throws Exception {
    CodeDetail codeDetail = new CodeDetail();
    codeDetail.setClassCode(classCode);
    codeDetail.setCodeValue(codeValue);

    return new ResponseEntity<>(codeDetailService.read(codeDetail), HttpStatus.OK);
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseEntity<List<CodeDetail>> list() throws Exception {
    logger.info("list");

    return new ResponseEntity<>(codeDetailService.list(), HttpStatus.OK);
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  public ResponseEntity<Void> register(@Validated @RequestBody CodeDetail codeDetail, UriComponentsBuilder uriBuilder) throws Exception {
    logger.info("register");

    codeDetailService.register(codeDetail);

    logger.info("register codeDetail.getCodeClassNo() = " + codeDetail.getClassCode());
    logger.info("register codeDetail.getCodeValue() = " + codeDetail.getCodeValue());

    URI resourceUri = uriBuilder.path("codedetails/{classCode}/{codeValue}")
            .buildAndExpand(codeDetail.getClassCode(), codeDetail.getCodeValue())
            .encode()
            .toUri();

    return ResponseEntity.created(resourceUri).build();
  }

  @RequestMapping(value = "/{classCode}/{codeValue}", method = RequestMethod.DELETE)
  public ResponseEntity<Void> remove(@PathVariable("classCode") String classCode, @PathVariable("codeValue") String codeValue) throws Exception {
    CodeDetail codeDetail = new CodeDetail();
    codeDetail.setClassCode(classCode);
    codeDetail.setCodeValue(codeValue);

    codeDetailService.remove(codeDetail);

    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "/{classCode}/{codeValue}", method = RequestMethod.PUT)
  public ResponseEntity<Void> modify(@PathVariable("classCode") String classCode, @PathVariable("codeValue") String codeValue, @Validated @RequestBody CodeDetail codeDetail) throws Exception {
    codeDetail.setClassCode(classCode);
    codeDetail.setCodeValue(codeValue);

    codeDetailService.modify(codeDetail);

    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
  }
}
