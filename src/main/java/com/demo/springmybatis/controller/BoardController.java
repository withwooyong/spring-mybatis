package com.demo.springmybatis.controller;

import com.demo.springmybatis.common.domain.PageRequest;
import com.demo.springmybatis.common.domain.Pagination;
import com.demo.springmybatis.common.util.AuthUtil;
import com.demo.springmybatis.domain.Board;
import com.demo.springmybatis.domain.Member;
import com.demo.springmybatis.domain.Page;
import com.demo.springmybatis.service.BoardService;
import com.demo.springmybatis.service.MemberService;
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

@RestController
@RequestMapping("/boards")
public class BoardController {

  @Autowired
  private BoardService service;

  @Autowired
  private MemberService memberService;

  private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

  @RequestMapping(value = "", method = RequestMethod.POST)
  @PreAuthorize("hasRole('ROLE_MEMBER')")
  public ResponseEntity<Void> register(@Validated @RequestBody Board board, UriComponentsBuilder uriBuilder, @RequestHeader(name = "Authorization") String header) throws Exception {
    int userNo = AuthUtil.getUserNo(header);
    logger.info("register userNo = " + userNo);

    Member member = memberService.read(userNo);

    logger.info("register member.getUserId() = " + member.getUserId());

    board.setWriter(member.getUserId());

    service.register(board);

    logger.info("register board.getBoardNo() = " + board.getBoardNo());

    URI resourceUri = uriBuilder.path("boards/{boardNo}")
            .buildAndExpand(board.getBoardNo())
            .encode()
            .toUri();

    return ResponseEntity.created(resourceUri).build();
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseEntity<Page> list(PageRequest pageRequest) throws Exception {

    logger.info("list pageRequest.getPage() = " + pageRequest.getPage());
    logger.info("list pageRequest.getSizePerPage() = " + pageRequest.getSizePerPage());
    logger.info("list pageRequest.getSearchType() = " + pageRequest.getSearchType());
    logger.info("list pageRequest.getKeyword() = " + pageRequest.getKeyword());

    logger.info("list");

    Pagination pagination = new Pagination();
    pagination.setPageRequest(pageRequest);

    pagination.setTotalCount(service.count(pageRequest));

    Page page = new Page();
    page.setList(service.list(pageRequest));
    page.setPagination(pagination);

    return new ResponseEntity<>(page, HttpStatus.OK);
  }

  @RequestMapping(value = "/{boardNo}", method = RequestMethod.GET)
  public ResponseEntity<Board> read(@PathVariable("boardNo") int boardNo) throws Exception {
    logger.info("read");

    Board board = service.read(boardNo);

    return new ResponseEntity<>(board, HttpStatus.OK);
  }

  @RequestMapping(value = "/{boardNo}", method = RequestMethod.DELETE)
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
  public ResponseEntity<Void> remove(@PathVariable("boardNo") int boardNo) throws Exception {
    logger.info("remove");

    service.remove(boardNo);

    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "/{boardNo}", method = RequestMethod.PUT)
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
  public ResponseEntity<Void> modify(@PathVariable("boardNo") int boardNo, @Validated @RequestBody Board board) throws Exception {
    logger.info("modify");

    board.setBoardNo(boardNo);
    service.modify(board);

    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
  }
}
