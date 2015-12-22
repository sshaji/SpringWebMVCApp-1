package com.shaji.javaee.offers.controllers.rest;

public class RestLoginResponse {
	private String access_token;

	public RestLoginResponse(String access_token) {
		this.access_token = access_token;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

}
