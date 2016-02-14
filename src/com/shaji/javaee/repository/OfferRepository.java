package com.shaji.javaee.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.shaji.javaee.model.Offer;

@Component("offerRepository")
public interface OfferRepository extends MongoRepository<Offer, String> {

}