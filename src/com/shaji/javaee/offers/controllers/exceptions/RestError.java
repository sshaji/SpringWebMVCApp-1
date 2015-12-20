package com.shaji.javaee.offers.controllers.exceptions;

public class RestError {
	private String error;

	public RestError() {

	}

	public RestError(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
