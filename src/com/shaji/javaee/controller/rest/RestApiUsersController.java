package com.shaji.javaee.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shaji.javaee.controller.rest.exceptions.DatabaseErrorException;
import com.shaji.javaee.controller.rest.exceptions.InvalidLoginException;
import com.shaji.javaee.controller.rest.exceptions.RecordNotFoundException;
import com.shaji.javaee.controller.rest.login.RestApiLoginHandler;
import com.shaji.javaee.model.User;
import com.shaji.javaee.service.UsersService;

@RestController
@RequestMapping(value = "/v1")
public class RestApiUsersController {

	@Autowired
	private UsersService usersService;

	/**
	 * Get current user details
	 * 
	 * @param id
	 * @return
	 * @throws RecordNotFoundException
	 * @throws DatabaseErrorException
	 * @throws InvalidLoginException
	 */
	@RequestMapping(value = "/me", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<User>
			me(@RequestHeader(name = "access_token", required = false, defaultValue = "") String accessToken)
					throws InvalidLoginException, RecordNotFoundException, DatabaseErrorException {
		if (!RestApiLoginHandler.isValidLogin(accessToken)) {
			throw new InvalidLoginException();
		}
		try {
			User retUser = usersService.getUserByUserName("asha");
			if (retUser != null) {
				return new ResponseEntity<User>(retUser, HttpStatus.OK);
			} else {
				throw new RecordNotFoundException();
			}
		} catch (DataAccessException ex) {
			throw new DatabaseErrorException(ex);
		}
	}

}
