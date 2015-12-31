package com.shaji.javaee.controller.rest.login;

public class RestApiLoginResponse {
	private String access_token;

	public RestApiLoginResponse(String access_token) {
		this.access_token = access_token;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

}
