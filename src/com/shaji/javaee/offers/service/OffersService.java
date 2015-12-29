package com.shaji.javaee.offers.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shaji.javaee.offers.model.dao.OffersDAO;
import com.shaji.javaee.offers.model.form.Offer;

@Service("offersService")
public class OffersService {

	@Autowired
	private OffersDAO offersDao;

	public List<Offer> getOffers(int offset, int limit, String searchString) {
		return offersDao.getOffers(offset, limit, searchString);
	}

	public Offer getOfferById(int id) {
		return offersDao.getOfferById(id);
	}

	public int createOffer(Offer offer) {
		return offersDao.createOffer(offer);
	}

	public boolean updateOffer(Offer offer) {
		return offersDao.updateOffer(offer);
	}

	public boolean deleteOffer(int id) {
		return offersDao.deleteOffer(id);
	}

}
