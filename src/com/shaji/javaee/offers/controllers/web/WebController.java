package com.shaji.javaee.offers.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
	@RequestMapping(value = "/")
	public String showHome() {
		// return "home";
		return "home-backbone";
	}
}
