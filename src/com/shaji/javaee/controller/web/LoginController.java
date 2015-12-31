package com.shaji.javaee.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	@RequestMapping(value = "/login")
	public String showLogin() {
		return "login";
	}
}
