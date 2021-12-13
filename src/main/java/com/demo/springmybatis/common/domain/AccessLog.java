package com.demo.springmybatis.common.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class AccessLog implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String requestUri;
	private String className;
	private String classSimpleName;
	private String methodName;
	private String remoteAddr;
	private Date regDate;
}
