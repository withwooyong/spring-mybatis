package com.demo.springmybatis.controller;

import com.demo.springmybatis.domain.CodeClass;
import com.demo.springmybatis.service.CodeClassService;
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
@RequestMapping("/codeclasses")
public class CodeClassController {

  private static final Logger logger = LoggerFactory.getLogger(CodeClassController.class);

  @Autowired
  private CodeClassService service;

  @RequestMapping(value = "/{classCode}", method = RequestMethod.GET)
  public ResponseEntity<CodeClass> read(@PathVariable("classCode") String classCode) throws Exception {
    logger.info("read {}", classCode);
    CodeClass codeClass = service.read(classCode);

    return new ResponseEntity<>(codeClass, HttpStatus.OK);
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseEntity<List<CodeClass>> list() throws Exception {
    logger.info("list");

    return new ResponseEntity<>(service.list(), HttpStatus.OK);
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  public ResponseEntity<Void> register(@Validated @RequestBody CodeClass codeClass, UriComponentsBuilder uriBuilder) throws Exception {
    logger.info("register");

    service.register(codeClass);

    logger.info("register codeClass.getCodeClassNo() = " + codeClass.getClassCode());

    URI resourceUri = uriBuilder.path("codeclasses/{classCode}")
            .buildAndExpand(codeClass.getClassCode())
            .encode()
            .toUri();

    return ResponseEntity.created(resourceUri).build();
  }

  @RequestMapping(value = "/{classCode}", method = RequestMethod.DELETE)
  public ResponseEntity<Void> remove(@PathVariable("classCode") String classCode) throws Exception {
    logger.info("remove {}", classCode);
    service.remove(classCode);

    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "/{classCode}", method = RequestMethod.PUT)
  public ResponseEntity<Void> modify(@PathVariable("classCode") String classCode, @Validated @RequestBody CodeClass codeClass) throws Exception {
    logger.info("modify {}", classCode);
    codeClass.setClassCode(classCode);
    service.modify(codeClass);

    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
  }
}
