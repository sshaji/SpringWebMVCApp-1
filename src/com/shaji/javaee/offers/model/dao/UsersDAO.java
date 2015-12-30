package com.shaji.javaee.offers.model.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shaji.javaee.offers.model.form.User;

@Transactional
@Repository("usersDao")
public class UsersDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Session session() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Create a user
	 * 
	 * @param user
	 * @return id of the created record
	 */
	public String createUser(User user) {
		session().save(user);
		session().flush();
		return user.getUserName();
	}

	/**
	 * Delete a user
	 * 
	 * @param user
	 * @return
	 */
	public boolean deleteUser(String username) {
		session().delete(new User(username));
		session().flush();
		return true;
	}

}
