package com.xyz.responses;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Response")
public class MyListNoPagingApiResponse<T> {

	@ApiModelProperty(value = "Confirm if the request has a error", required = true)
	private boolean error;
	
	@ApiModelProperty(value = "Show error of the request", required = true)
	private String message;
	
	@ApiModelProperty(value = "The models corresponding to the request", required = true)
	private List<T> content;

	public MyListNoPagingApiResponse() {
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

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}
}
