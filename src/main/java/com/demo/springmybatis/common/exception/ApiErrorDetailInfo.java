package com.demo.springmybatis.common.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiErrorDetailInfo {

	private final String target;
	private final String message;

	@Builder
	public ApiErrorDetailInfo(String target, String message) {
		this.target = target;
		this.message = message;
	}
}
