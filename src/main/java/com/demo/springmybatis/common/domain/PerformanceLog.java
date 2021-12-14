package com.demo.springmybatis.common.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class PerformanceLog implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String signatureName;
	private String signatureTypeName;
	private long durationTime;
	private LocalDateTime regDate;
}
