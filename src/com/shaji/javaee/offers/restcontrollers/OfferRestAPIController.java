package com.shaji.javaee.offers.restcontrollers;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shaji.javaee.offers.model.Offer;
import com.shaji.javaee.offers.model.OffersDAO;

@RestController
@RequestMapping(value = "/v1")
public class OfferRestAPIController {

	/**
	 * Get all
	 * 
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public @ResponseBody List<Offer> get() {
		ApplicationContext context = new ClassPathXmlApplicationContext("com/shaji/javaee/offers/beans/beans.xml");
		OffersDAO offersDao = (OffersDAO) context.getBean("offersDao");
		List<Offer> retOffers = offersDao.getOffers();
		((ClassPathXmlApplicationContext) context).close();
		return retOffers;
	}

	/**
	 * Get by id
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public @ResponseBody Offer get(@PathVariable("id") int id) {
		ApplicationContext context = new ClassPathXmlApplicationContext("com/shaji/javaee/offers/beans/beans.xml");
		OffersDAO offersDao = (OffersDAO) context.getBean("offersDao");
		Offer retOffer = offersDao.getOfferById(id);
		((ClassPathXmlApplicationContext) context).close();
		return retOffer;
	}

	/**
	 * Create one
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public @ResponseBody Offer post(@RequestBody Offer offer) {
		ApplicationContext context = new ClassPathXmlApplicationContext("com/shaji/javaee/offers/beans/beans.xml");
		OffersDAO offersDao = (OffersDAO) context.getBean("offersDao");
		Offer retOffer = null;
		offersDao.createOffer(offer);
		retOffer = offer;
		((ClassPathXmlApplicationContext) context).close();
		return retOffer;
	}

	/**
	 * Update one
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.PUT)
	public @ResponseBody Offer put(@RequestBody Offer offer) {
		ApplicationContext context = new ClassPathXmlApplicationContext("com/shaji/javaee/offers/beans/beans.xml");
		OffersDAO offersDao = (OffersDAO) context.getBean("offersDao");
		Offer retOffer = null;
		offersDao.updateOffer(offer);
		retOffer = offer;
		((ClassPathXmlApplicationContext) context).close();
		return retOffer;
	}

	/**
	 * Mass create
	 * 
	 * @param users
	 * @return
	 */
	@RequestMapping(value = "/users/masscreate", method = RequestMethod.POST)
	public @ResponseBody List<Offer> post(@RequestBody List<Offer> offers) {
		ApplicationContext context = new ClassPathXmlApplicationContext("com/shaji/javaee/offers/beans/beans.xml");
		OffersDAO offersDao = (OffersDAO) context.getBean("offersDao");
		List<Offer> retOffers = null;
		offersDao.createOffers(offers);
		retOffers = offers;
		((ClassPathXmlApplicationContext) context).close();
		return retOffers;
	}
}
