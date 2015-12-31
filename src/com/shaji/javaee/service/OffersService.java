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
		return offerRepository.get(offset, limit, searchString);
	}

	public Offer getOfferById(int id) {
		return offerRepository.getById(id);
	}

	public Offer createOffer(Offer offer) {
		return offerRepository.create(offer);
	}

	public Offer updateOffer(Offer offer) {
		return offerRepository.update(offer);
	}

	public boolean deleteOffer(int id) {
		return offerRepository.delete(id);
	}

}
