package com.shaji.javaee.controller.rest.error;

public class RestApiError {
	private String error;

	public RestApiError() {

	}

	public RestApiError(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
