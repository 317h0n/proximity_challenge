package com.xyz.responses;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Response")
public class MyListApiResponse<T> {

	@ApiModelProperty(value = "Confirm if the request has a error", required = true)
	private boolean error;
	
	@ApiModelProperty(value = "Show error of the request", required = true)
	private String message;
	
	@ApiModelProperty(value = "Total elements of the request", required = true)
	private long elements;
	
	@ApiModelProperty(value = "Total pages of the request", required = true)
	private int pages;
	
	@ApiModelProperty(value = "Current page of the request", required = true)
	private int page;
	
	@ApiModelProperty(value = "The models corresponding to the request", required = true)
	private List<T> content;

	public MyListApiResponse() {
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getElements() {
		return elements;
	}

	public void setElements(long elements) {
		this.elements = elements;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}
}
