package com.demo.springmybatis.common.domain;

import lombok.Getter;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
public class Pagination {

	private int totalCount;
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;
	private final int displayPageNum = 10;
	private PageRequest pageRequest;

	public void setPageRequest(PageRequest pageRequest) {
		this.pageRequest = pageRequest;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcData();
	}

	private void calcData() {
		endPage = (int) (Math.ceil(pageRequest.getPage() / (double) displayPageNum) * displayPageNum);
		startPage = (endPage - displayPageNum) + 1;
		int tempEndPage = (int) (Math.ceil(totalCount / (double) pageRequest.getSizePerPage()));

		if (endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		prev = startPage != 1;
		next = endPage * pageRequest.getSizePerPage() < totalCount;
	}

	public String makeQuery(int page) {
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
				.queryParam("page", page)
				.queryParam("perPageNum", pageRequest.getSizePerPage()).build();
		return uriComponents.toUriString();
	}
}
