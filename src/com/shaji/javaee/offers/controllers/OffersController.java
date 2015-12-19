package com.shaji.javaee.offers.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OffersController {
	@RequestMapping(value = "/")
	public String showHome(Model model) {
		model.addAttribute("test", "Test");
		return "home";
	}
	
	@RequestMapping(value = "/2")
	public String showHomeNoAjax(Model model) {
		model.addAttribute("test", "Test");
		return "home2";
	}
}
