package com.shaji.javaee.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.shaji.javaee.model.User;

@Component("userRepository")
public interface UserRepository extends MongoRepository<User, String> {

	public User findByUsername(String username);

}