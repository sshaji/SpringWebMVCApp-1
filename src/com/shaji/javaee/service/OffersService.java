package com.shaji.javaee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shaji.javaee.model.Offer;
import com.shaji.javaee.repository.OfferRepository;

@Service("offersService")
public class OffersService {

	@Autowired
	private OfferRepository offerRepository;

	public List<Offer> getOffers(int offset, int limit, String searchString) {
		return offerRepository.findAll();
	}

	public Offer getOfferById(String id) {
		return offerRepository.findOne(id);
	}

	public Offer createOffer(Offer offer) {
		return offerRepository.save(offer);
	}

	public Offer updateOffer(Offer offer) {
		return offerRepository.save(offer);
	}

	public boolean deleteOffer(String id) {
		offerRepository.delete(id);
		return true;
	}

}
