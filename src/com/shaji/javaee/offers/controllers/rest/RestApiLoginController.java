package com.shaji.javaee.offers.controllers.rest;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1")
public class RestApiLoginController {

	/**
	 * Login
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<RestLoginResponse> login(HttpSession session) {
		Integer randomNum = new Random().nextInt(50) + 1;
		session.setAttribute("token", randomNum.toString());
		return new ResponseEntity<RestLoginResponse>(new RestLoginResponse(randomNum.toString()), HttpStatus.OK);
	}

}
