package com.demo.springmybatis.controller;

import com.demo.springmybatis.domain.Member;
import com.demo.springmybatis.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/users")
public class MemberController {

  @Autowired
  private MemberService service;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private MessageSource messageSource;

  private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

  @RequestMapping(value = "", method = RequestMethod.POST)
  public ResponseEntity<Void> register(@Validated @RequestBody Member member, UriComponentsBuilder uriBuilder) throws Exception {
    logger.info("member.getUserName() = " + member.getUserName());

    String inputPassword = member.getUserPw();
    member.setUserPw(passwordEncoder.encode(inputPassword));

    service.register(member);

    logger.info("register member.getUserNo() = " + member.getUserNo());

    URI resourceUri = uriBuilder.path("users/{userNo}")
            .buildAndExpand(member.getUserNo())
            .encode()
            .toUri();

    return ResponseEntity.created(resourceUri).build();
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseEntity<List<Member>> list() throws Exception {
    return new ResponseEntity<>(service.list(), HttpStatus.OK);
  }

  @RequestMapping(value = "/{userNo}", method = RequestMethod.GET)
  public ResponseEntity<Member> read(@PathVariable("userNo") int userNo) throws Exception {
    Member member = service.read(userNo);

    return new ResponseEntity<>(member, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @RequestMapping(value = "/{userNo}", method = RequestMethod.DELETE)
  public ResponseEntity<Void> remove(@PathVariable("userNo") int userNo) throws Exception {
    service.remove(userNo);

    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "/{userNo}", method = RequestMethod.PUT)
  public ResponseEntity<Void> modify(@PathVariable("userNo") int userNo, @Validated @RequestBody Member member) throws Exception {
    logger.info("modify : member.getUserName() = " + member.getUserName());
    logger.info("modify : userNo = " + userNo);

    member.setUserNo(userNo);
    service.modify(member);

    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "/setup", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
  public ResponseEntity<String> setupAdmin(@Validated @RequestBody Member member) throws Exception {
    logger.info("setupAdmin : member.getUserName() = " + member.getUserName());
    logger.info("setupAdmin : service.countAll() = " + service.countAll());

    if (service.countAll() == 0) {
      String inputPassword = member.getUserPw();
      member.setUserPw(passwordEncoder.encode(inputPassword));

      member.setJob("00");

      service.setupAdmin(member);

      return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    String message = messageSource.getMessage("common.cannotSetupAdmin", null, Locale.KOREAN);

    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
  }
}
