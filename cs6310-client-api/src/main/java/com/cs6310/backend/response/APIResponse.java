package com.cs6310.backend.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APIResponse {
	@Expose
	private ResponseStatus status;

	@Expose
	private String errorCause;
	
	@Expose
	@SerializedName("response")
	private Object result;

	public APIResponse() {
		this.status = ResponseStatus.OK;

	}
	
	public Object getResult() {
		return result;
	}
	
	public void setResult(Object result) {
		this.result = result;
	}
	
	public ResponseStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status){
		this.status = status;
	}

	public String getErrorCause() {
		return errorCause;
	}

	public void setErrorCause(String errorCause) {
		this.errorCause = errorCause;
	}

	
	
}
