package com.shaji.javaee.offers.model.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shaji.javaee.offers.model.form.Offer;

@Transactional
@Repository("offersDao")
public class OffersDAO {

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
	public List<Offer> getOffers(int offset, int limit, String searchString) {
		Criteria criteria = session().createCriteria(Offer.class);
		if (offset > 0) {
			criteria.setFirstResult(offset);
		}
		if (limit > 0) {
			criteria.setMaxResults(limit);
		}
		if (!"".equals(searchString)) {
			criteria.add(Restrictions.disjunction().add(Restrictions.like("name", "%" + searchString + "%"))
					.add(Restrictions.like("email", "%" + searchString + "%"))
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
	public Offer getOfferById(int id) {
		Criteria criteria = session().createCriteria(Offer.class).add(Restrictions.idEq(id));
		return (Offer) criteria.uniqueResult();
	}

	/**
	 * Create an offer
	 * 
	 * @param offer
	 * @return id of the created record
	 */
	public int createOffer(Offer offer) {
		session().save(offer);
		session().flush();
		return offer.getId();
	}

	/**
	 * Update an offer
	 * 
	 * @param offer
	 * @return
	 */
	public boolean updateOffer(Offer offer) {
		session().update(offer);
		session().flush();
		return true;
	}

	/**
	 * Delete an offer
	 * 
	 * @param id
	 * @param offer
	 * @return
	 */
	public boolean deleteOffer(int id) {
		session().delete(new Offer(id));
		session().flush();
		return true;
	}

}
