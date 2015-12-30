package com.shaji.javaee.offers.model.dao.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shaji.javaee.offers.model.dao.UsersDAO;
import com.shaji.javaee.offers.model.form.User;

import junit.framework.TestCase;

@ActiveProfiles("test")
@ContextConfiguration(locations = { "classpath:com/shaji/javaee/offers/test/config/test-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class UsersDAOTest extends TestCase {

	@Autowired
	private UsersDAO usersDao;

	@Test
	public void testCreateUsers() {
		User user1 = new User("john", "john", 1, "John", "Test", "john@test.com");
		User user2 = new User("ted", "ted", 1, "Ted", "Test", "ted@test.com");

		// Create 3 offers
		String username1 = usersDao.createUser(user1);
		String username2 = usersDao.createUser(user2);

		assertNotNull("Create user should return the username of the record1", username1);
		assertNotNull("Create user should return the username of the record2", username2);

		// Clean
		usersDao.deleteUser(user1.getUserName());
		usersDao.deleteUser(user2.getUserName());
	}

}
