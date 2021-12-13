package com.demo.springmybatis.controller;

import com.demo.springmybatis.domain.Notice;
import com.demo.springmybatis.service.NoticeService;
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
@RequestMapping("/notices")
public class NoticeController {
	
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);

	@Autowired
	private NoticeService service;

	@RequestMapping(value = "/{noticeNo}", method = RequestMethod.GET)
	public ResponseEntity<Notice> read(@PathVariable("noticeNo") int noticeNo) throws Exception {
		Notice notice = service.read(noticeNo);
			
		return new ResponseEntity<>(notice, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Notice>> list() throws Exception {
		logger.info("list");
		
		return new ResponseEntity<>(service.list(), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Void> register(@Validated @RequestBody Notice notice, UriComponentsBuilder uriBuilder) throws Exception {
		logger.info("register");
		
		service.register(notice);
		
		logger.info("register notice.getNoticeNo() = " + notice.getNoticeNo());
		
		URI resourceUri = uriBuilder.path("notices/{noticeNo}")
				.buildAndExpand(notice.getNoticeNo())
				.encode()
				.toUri();

		return ResponseEntity.created(resourceUri).build();
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/{noticeNo}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> remove(@PathVariable("noticeNo") int noticeNo) throws Exception {
		service.remove(noticeNo);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/{noticeNo}", method = RequestMethod.PUT)
	public ResponseEntity<Void> modify(@PathVariable("noticeNo") int noticeNo, @Validated @RequestBody Notice notice) throws Exception {
		notice.setNoticeNo(noticeNo);
		service.modify(notice);
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
