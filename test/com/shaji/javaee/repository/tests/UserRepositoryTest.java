package com.shaji.javaee.repository.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shaji.javaee.model.User;
import com.shaji.javaee.repository.UserRepository;

import junit.framework.TestCase;

@ActiveProfiles("test")
@ContextConfiguration(locations = { "classpath:com/shaji/javaee/test/config/test-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class UserRepositoryTest extends TestCase {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testCreateUsers() {
		User user1 = new User("john", "john", 1, "John", "Test", "john@test.com");
		User user2 = new User("ted", "ted", 1, "Ted", "Test", "ted@test.com");

		// Create 3 offers
		user1 = userRepository.create(user1);
		user2 = userRepository.create(user2);

		assertNotNull("Create user should return the username of the record1", user1.getUserName());
		assertNotNull("Create user should return the username of the record2", user2.getUserName());

		// Clean
		userRepository.delete(user1.getUserName());
		userRepository.delete(user2.getUserName());
	}

}
