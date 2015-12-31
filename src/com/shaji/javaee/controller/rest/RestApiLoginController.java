package com.shaji.javaee.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shaji.javaee.controller.rest.login.RestApiLoginHandler;
import com.shaji.javaee.controller.rest.login.RestApiLoginResponse;

@RestController
@RequestMapping(value = "/v1")
public class RestApiLoginController {

	/**
	 * Login
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<RestApiLoginResponse> login() {
		return new ResponseEntity<RestApiLoginResponse>(RestApiLoginHandler.dummyLogin(), HttpStatus.OK);
	}

}
