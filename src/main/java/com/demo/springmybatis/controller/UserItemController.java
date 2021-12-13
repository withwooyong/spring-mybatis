package com.demo.springmybatis.controller;

import com.demo.springmybatis.common.util.AuthUtil;
import com.demo.springmybatis.domain.UserItem;
import com.demo.springmybatis.exception.NotMyItemException;
import com.demo.springmybatis.service.UserItemService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/useritems")
public class UserItemController {

  @Autowired
  private UserItemService service;

  @Value("${upload.path}")
  private String uploadPath;

  private static final Logger logger = LoggerFactory.getLogger(UserItemController.class);

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
  @RequestMapping(value = "/list/all", method = RequestMethod.GET)
  public ResponseEntity<List<UserItem>> listAll() throws Exception {
    logger.info("listAll");

    return new ResponseEntity<>(service.listAll(), HttpStatus.OK);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseEntity<List<UserItem>> list(@RequestHeader(name = "Authorization") String header) throws Exception {
    logger.info("read : header " + header);

    int userNo = AuthUtil.getUserNo(header);

    logger.info("read : userNo " + userNo);

    return new ResponseEntity<>(service.list(userNo), HttpStatus.OK);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
  @RequestMapping(value = "/{userItemNo}", method = RequestMethod.GET)
  public ResponseEntity<UserItem> read(@PathVariable("userItemNo") int userItemNo) throws Exception {
    UserItem userItem = service.read(userItemNo);

    return new ResponseEntity<>(userItem, HttpStatus.OK);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
  @RequestMapping("/download/{userItemNo}")
  public ResponseEntity<byte[]> download(@PathVariable("userItemNo") int userItemNo, @RequestHeader(name = "Authorization") String header) throws Exception {
    logger.info("download userItemNo = " + userItemNo);

    UserItem userItem = service.read(userItemNo);

    int userNo = AuthUtil.getUserNo(header);
    logger.info("download userNo = " + userNo);

    if (userItem.getUserNo() != userNo) {
      throw new NotMyItemException("It is Not My Item.");
    }

    String fullName = userItem.getPictureUrl();

    InputStream in = null;
    ResponseEntity<byte[]> entity = null;

    try {
      HttpHeaders headers = new HttpHeaders();

      in = new FileInputStream(uploadPath + File.separator + fullName);

      String fileName = fullName.substring(fullName.indexOf("_") + 1);

      headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
      headers.add("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");

      entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
    } catch (Exception e) {
      e.printStackTrace();
      entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
    } finally {
      in.close();
    }

    return entity;
  }
}
