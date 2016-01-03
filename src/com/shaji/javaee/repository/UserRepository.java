package com.shaji.javaee.repository;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shaji.javaee.model.User;

@Transactional
@Repository("userRepository")
public class UserRepository {

	@Autowired
	private SessionFactory sessionFactory;

	private Session session() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Get a user
	 * 
	 * @param userName
	 * @return
	 */
	public User getById(String userName) {
		Criteria criteria = session().createCriteria(User.class).add(Restrictions.eq("userName", userName));
		return (User) criteria.uniqueResult();
	}

	/**
	 * Create a user
	 * 
	 * @param user
	 * @return id of the created record
	 */
	public User create(User user) {
		session().save(user);
		return user;
	}

	/**
	 * Delete a user
	 * 
	 * @param user
	 * @return
	 */
	public boolean delete(String username) {
		session().delete(new User(username));
		return true;
	}

}
