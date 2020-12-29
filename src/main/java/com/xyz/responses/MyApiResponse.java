package com.xyz.responses;

import io.swagger.annotations.ApiModelProperty;

public class MyApiResponse<T> {

	@ApiModelProperty(value = "Confirm if the request has a error", required = true)
	private boolean error;
	
	@ApiModelProperty(value = "Show error of the request", required = true)
	private String message;
	
	@ApiModelProperty(value = "The models corresponding to the request", required = true)
	private T content;

	public MyApiResponse() {
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

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

}
