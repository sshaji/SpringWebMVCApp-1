package com.shaji.javaee.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shaji.javaee.model.Offer;

@Transactional
@Repository("offerRepository")
public class OfferRepository {

	@Autowired
	private SessionFactory sessionFactory;

	private Session session() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Get all offers
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Offer> get(int offset, int limit, String searchString) {
		Criteria criteria = session().createCriteria(Offer.class);
		if (offset > 0) {
			criteria.setFirstResult(offset);
		}
		if (limit > 0) {
			criteria.setMaxResults(limit);
		}
		if (!"".equals(searchString)) {
			criteria.createAlias("user", "u")
					.add(Restrictions.disjunction().add(Restrictions.like("u.firstName", "%" + searchString + "%"))
							.add(Restrictions.like("u.lastName", "%" + searchString + "%"))
							.add(Restrictions.like("u.email", "%" + searchString + "%"))
							.add(Restrictions.like("offerDetails", "%" + searchString + "%")));
		}
		return criteria.list();
	}

	/**
	 * Get one offer by id
	 * 
	 * @param id
	 * @return
	 */
	public Offer getById(int id) {
		Criteria criteria = session().createCriteria(Offer.class).add(Restrictions.idEq(id));
		return (Offer) criteria.uniqueResult();
	}

	/**
	 * Create an offer
	 * 
	 * @param offer
	 * @return created record
	 */
	public Offer create(Offer offer) {
		session().save(offer);
		return offer;
	}

	/**
	 * Update an offer
	 * 
	 * @param offer
	 * @return
	 */
	public Offer update(Offer offer) {
		session().update(offer);
		return offer;
	}

	/**
	 * Delete an offer
	 * 
	 * @param id
	 * @param offer
	 * @return
	 */
	public boolean delete(int id) {
		session().delete(new Offer(id));
		return true;
	}

}
