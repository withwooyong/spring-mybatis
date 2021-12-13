package com.demo.springmybatis.common.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class CodeLabelValue implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String label;
	private String value;

	public CodeLabelValue() {
		super();
	}

	public CodeLabelValue(String value, String label) {
		this.value = value;
		this.label = label;
	}
}
