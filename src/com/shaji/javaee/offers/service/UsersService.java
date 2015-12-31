package com.shaji.javaee.offers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shaji.javaee.offers.model.dao.UsersDAO;
import com.shaji.javaee.offers.model.form.User;

@Service("usersService")
public class UsersService {

	@Autowired
	private UsersDAO usersDao;

	public User getUserByUserName(String userName) {
		return usersDao.getUserByUserName(userName);
	}

}
