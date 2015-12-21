package com.shaji.javaee.offers.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
	@RequestMapping(value = "/")
	public String showHome() {
		return "home-angular";
	}

	@RequestMapping(value = "/angular")
	public String showHome2() {
		return "home-angular";
	}

	@RequestMapping(value = "/backbone")
	public String showHome3() {
		return "home-backbone";
	}

	@RequestMapping(value = "/server")
	public String showHome4() {
		return "home";
	}
}
