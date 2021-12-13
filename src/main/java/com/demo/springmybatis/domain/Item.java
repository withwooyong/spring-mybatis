package com.demo.springmybatis.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Item implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer itemId;
  private String itemName;
  private Integer price;
  private String description;
  private MultipartFile picture;
  private String pictureUrl;
  private MultipartFile preview;
  private String previewUrl;
}
