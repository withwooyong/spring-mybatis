package com.demo.springmybatis.common.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ApiErrorInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String message;
	private final List<ApiErrorDetailInfo> details = new ArrayList<ApiErrorDetailInfo>();
	
	public void addDetailInfo(String target, String message) {
		details.add(new ApiErrorDetailInfo(target, message));
	}
	
}
