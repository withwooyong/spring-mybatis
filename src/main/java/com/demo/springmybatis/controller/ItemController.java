package com.demo.springmybatis.controller;

import com.demo.springmybatis.common.util.AuthUtil;
import com.demo.springmybatis.domain.Item;
import com.demo.springmybatis.domain.Member;
import com.demo.springmybatis.service.ItemService;
import com.demo.springmybatis.service.MemberService;
import com.demo.springmybatis.service.UserItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/items")
public class ItemController {

  @Autowired
  private ItemService itemService;

  @Value("${upload.path}")
  private String uploadPath;

  @Autowired
  private MemberService memberService;

  @Autowired
  private UserItemService userItemService;

  @Autowired
  private MessageSource messageSource;

  private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseEntity<List<Item>> list() throws Exception {
    return new ResponseEntity<>(itemService.list(), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @RequestMapping(value = "", method = RequestMethod.POST)
  public ResponseEntity<Void> register(@RequestPart("item") String itemString, @RequestPart("file") MultipartFile originalImageFile, @RequestPart("file2") MultipartFile previewImageFile, UriComponentsBuilder uriBuilder) throws Exception {
    logger.info("itemString: " + itemString);

    Item item = new ObjectMapper().readValue(itemString, Item.class);

    String itemName = item.getItemName();
    String description = item.getDescription();

    if (itemName != null) {
      logger.info("item.getItemName(): " + itemName);

      item.setItemName(itemName);
    }

    if (description != null) {
      logger.info("item.getDescription(): " + description);

      item.setDescription(description);
    }

    item.setPicture(originalImageFile);
    item.setPreview(previewImageFile);

    MultipartFile pictureFile = item.getPicture();
    MultipartFile previewFile = item.getPreview();

    if (pictureFile != null) {
      logger.info("register pictureFile != null " + pictureFile.getOriginalFilename());
    } else {
      logger.info("register pictureFile == null ");
    }

    String createdPictureFilename = uploadFile(pictureFile.getOriginalFilename(), pictureFile.getBytes());
    String createdPreviewFilename = uploadFile(previewFile.getOriginalFilename(), previewFile.getBytes());

    item.setPictureUrl(createdPictureFilename);
    item.setPreviewUrl(createdPreviewFilename);

    itemService.register(item);

    logger.info("register member.getItemId() = " + item.getItemId());

    URI resourceUri = uriBuilder.path("items/{itemId}")
            .buildAndExpand(item.getItemId())
            .encode()
            .toUri();

    return ResponseEntity.created(resourceUri).build();
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @RequestMapping(value = "/{itemId}", method = RequestMethod.PUT)
  public ResponseEntity<Void> modify(@PathVariable("itemId") int itemId, @RequestPart("item") String itemString, @RequestPart("file") MultipartFile originalImageFile, @RequestPart("file2") MultipartFile previewImageFile) throws Exception {

    logger.info("itemString: " + itemString);

    Item item = new ObjectMapper().readValue(itemString, Item.class);

    item.setItemId(itemId);

    String itemName = item.getItemName();
    String description = item.getDescription();

    if (itemName != null) {
      logger.info("item.getItemName(): " + itemName);

      item.setItemName(itemName);
    }

    if (description != null) {
      logger.info("item.getDescription(): " + description);

      item.setDescription(description);
    }

    item.setPicture(originalImageFile);
    item.setPreview(previewImageFile);

    MultipartFile pictureFile = item.getPicture();

    if (pictureFile != null && pictureFile.getSize() > 0) {
      String createdFilename = uploadFile(pictureFile.getOriginalFilename(), pictureFile.getBytes());

      item.setPictureUrl(createdFilename);
    }

    MultipartFile previewFile = item.getPreview();

    if (previewFile != null && previewFile.getSize() > 0) {
      String createdFilename = uploadFile(previewFile.getOriginalFilename(), previewFile.getBytes());

      item.setPreviewUrl(createdFilename);
    }

    itemService.modify(item);

    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @RequestMapping(value = "/{itemId}", method = RequestMethod.DELETE)
  public ResponseEntity<Void> remove(@PathVariable("itemId") int itemId) throws Exception {
    itemService.remove(itemId);

    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
  }

  private String uploadFile(String originalName, byte[] fileData) throws Exception {
    UUID uid = UUID.randomUUID();

    String createdFileName = uid.toString() + "_" + originalName;

    File target = new File(uploadPath, createdFileName);

    FileCopyUtils.copy(fileData, target);

    return createdFileName;
  }

  @RequestMapping("/display")
  public ResponseEntity<byte[]> displayFile(@RequestParam("itemId") int itemId) throws Exception {
    InputStream in = null;
    ResponseEntity<byte[]> entity = null;

    String fileName = itemService.getPicture(itemId);

    System.out.println("=================================");
    logger.info("displayFile itemId = " + itemId);
    logger.info("displayFile fileName = " + fileName);
    System.out.println("=================================");
    try {
      String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
      MediaType mType = getMediaType(formatName);
      HttpHeaders headers = new HttpHeaders();
      in = new FileInputStream(uploadPath + File.separator + fileName);

      if (mType != null) {
        headers.setContentType(mType);
      }
      entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
    } catch (Exception e) {
      e.printStackTrace();
      entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
    } finally {
      in.close();
    }
    return entity;
  }

  @RequestMapping("/preview")
  public ResponseEntity<byte[]> previewFile(@RequestParam("itemId") int itemId) throws Exception {
    InputStream in = null;
    ResponseEntity<byte[]> entity = null;

    String fileName = itemService.getPreview(itemId);

    logger.info("displayFile itemId = " + itemId);
    logger.info("displayFile fileName = " + fileName);

    try {
      String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);

      MediaType mType = getMediaType(formatName);

      HttpHeaders headers = new HttpHeaders();

      in = new FileInputStream(uploadPath + File.separator + fileName);

      if (mType != null) {
        headers.setContentType(mType);
      }

      entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
    } catch (Exception e) {
      e.printStackTrace();
      entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
    } finally {
      in.close();
    }
    return entity;
  }

  private MediaType getMediaType(String formatName) {
    if (formatName != null) {
      if (formatName.equals("JPG")) {
        return MediaType.IMAGE_JPEG;
      }

      if (formatName.equals("GIF")) {
        return MediaType.IMAGE_GIF;
      }

      if (formatName.equals("PNG")) {
        return MediaType.IMAGE_PNG;
      }
    }

    return null;
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
  @RequestMapping(value = "/download/{itemId}", method = RequestMethod.GET)
  public ResponseEntity<byte[]> downloadFile(@PathVariable("itemId") int itemId, @RequestHeader(name = "Authorization") String header) throws Exception {

    int userNo = AuthUtil.getUserNo(header);
    logger.info("downloadFile userNo = " + userNo);

    InputStream in = null;
    ResponseEntity<byte[]> entity = null;

    String fullName = itemService.getPicture(itemId);

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

  @RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
  public ResponseEntity<Item> read(@PathVariable("itemId") int itemId) throws Exception {
    Item item = itemService.read(itemId);
    return new ResponseEntity<>(item, HttpStatus.OK);
  }

  @RequestMapping(value = "/buy/{itemId}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
  public ResponseEntity<String> buy(@PathVariable("itemId") int itemId, @RequestHeader(name = "Authorization") String header) throws Exception {
    int userNo = AuthUtil.getUserNo(header);
    logger.info("buy userNo = " + userNo);

    Member member = memberService.read(userNo);
    member.setCoin(memberService.getCoin(userNo));
    Item item = itemService.read(itemId);
    userItemService.register(member, item);
    String message = messageSource.getMessage("item.purchaseComplete", null, Locale.KOREAN);
    return new ResponseEntity<>(message, HttpStatus.OK);
  }
}
