package com.shaji.javaee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shaji.javaee.model.User;
import com.shaji.javaee.repository.UserRepository;

@Service("usersService")
public class UsersService {

	@Autowired
	private UserRepository userRepository;

	public User getUserByUserName(String userName) {
		return userRepository.getByUserName(userName);
	}

}
