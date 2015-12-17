package com.shaji.javaee.offers.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shaji.javaee.offers.model.User;

@RestController
@RequestMapping(value = "/rest/v1/")
public class OfferRestAPIController {

	/**
	 * Get all
	 * 
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<User> get() {
		return new ArrayList<User>(Arrays.asList(new User(1, "User1"), new User(2, "User2")));
	}

	/**
	 * Get by id
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody User get(@PathVariable("id") int id) {
		return new User(id, "User");
	}

	/**
	 * Create one
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody User post(@RequestBody User user) {
		return user;
	}

	/**
	 * Mass create
	 * 
	 * @param users
	 * @return
	 */
	@RequestMapping(value = "/users/masscreate", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody List<User> post(@RequestBody List<User> users) {
		return users;
	}
}
