package com.shaji.javaee.controller.rest.login;

public class RestApiLoginHandler {

	public static boolean isValidLogin(String accessToken) {
		String token = "123456789";
		if (token.equals(accessToken)) {
			return true;
		} else {
			return false;
		}
	}

	public static RestApiLoginResponse dummyLogin() {
		return new RestApiLoginResponse("123456789");
	}
}
