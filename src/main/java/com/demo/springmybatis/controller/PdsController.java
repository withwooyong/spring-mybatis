package com.demo.springmybatis.controller;

import com.demo.springmybatis.common.util.UploadFileUtils;
import com.demo.springmybatis.domain.Pds;
import com.demo.springmybatis.service.PdsService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pds")
public class PdsController {

	@Autowired
	private PdsService pdsService;

	@Value("${upload.path}")
	private String uploadPath;
	
	private static final Logger logger = LoggerFactory.getLogger(PdsController.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Pds>> list() throws Exception {
		return new ResponseEntity<>(this.pdsService.list(), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Void> register(@Validated @RequestBody Pds pds, UriComponentsBuilder uriBuilder) throws Exception {
		this.pdsService.register(pds);

		logger.info("register pds.getItemId() = " + pds.getItemId());
		
		URI resourceUri = uriBuilder.path("pds/{itemId}")
				.buildAndExpand(pds.getItemId())
				.encode()
				.toUri();

		return ResponseEntity.created(resourceUri).build();
	}
	
	@RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
	public ResponseEntity<Pds> read(@PathVariable("itemId") int itemId) throws Exception {
		Pds pds = this.pdsService.read(itemId);

		return new ResponseEntity<>(pds, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/{itemId}", method = RequestMethod.PUT)
	public ResponseEntity<Void> modify(@PathVariable("itemId") int itemId, @Validated @RequestBody Pds pds) throws Exception {
		pds.setItemId(itemId);
		this.pdsService.modify(pds);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/{itemId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> remove(@PathVariable("itemId") int itemId) throws Exception {
		this.pdsService.remove(itemId);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> upload(MultipartFile file) throws Exception {
		String savedName = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());

		return new ResponseEntity<String>(savedName, HttpStatus.CREATED);
	}

	@RequestMapping("/download")
	public ResponseEntity<byte[]> download(String fullName) throws Exception {
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;

		pdsService.updateAttachDownCnt(fullName);

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

	@RequestMapping("/attach/{itemId}")
	public List<String> attach(@PathVariable("itemId") Integer itemId) throws Exception {
		return pdsService.getAttach(itemId);
	}

}
