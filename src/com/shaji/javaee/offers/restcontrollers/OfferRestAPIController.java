package com.shaji.javaee.offers.restcontrollers;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shaji.javaee.offers.model.Offer;
import com.shaji.javaee.offers.model.OffersDAO;

@RestController
@RequestMapping(value = "/v1")
public class OfferRestAPIController {

	private String DaoContextXmlUrl = "classpath:com/shaji/javaee/offers/config/dao-context.xml";

	/**
	 * Get all
	 * 
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public @ResponseBody List<Offer> getAll(
			@RequestParam(name = "limit", required = false, defaultValue = "5") int limit) {
		ApplicationContext context = new ClassPathXmlApplicationContext(DaoContextXmlUrl);
		OffersDAO offersDao = (OffersDAO) context.getBean("offersDao");
		List<Offer> retOffers = null;
		try {
			retOffers = offersDao.getOffers(limit);
		} catch (DataAccessException ex) {

		}
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
		ApplicationContext context = new ClassPathXmlApplicationContext(DaoContextXmlUrl);
		OffersDAO offersDao = (OffersDAO) context.getBean("offersDao");
		Offer retOffer = null;
		try {
			retOffer = offersDao.getOfferById(id);
		} catch (DataAccessException ex) {

		}
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
		ApplicationContext context = new ClassPathXmlApplicationContext(DaoContextXmlUrl);
		OffersDAO offersDao = (OffersDAO) context.getBean("offersDao");
		Offer retOffer = null;
		try {
			int retId = offersDao.createOffer(offer);
			if (retId != 0) {
				retOffer = offersDao.getOfferById(retId);
			}
		} catch (DataAccessException ex) {

		}
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
		ApplicationContext context = new ClassPathXmlApplicationContext(DaoContextXmlUrl);
		OffersDAO offersDao = (OffersDAO) context.getBean("offersDao");
		Offer retOffer = null;
		try {
			if (offersDao.updateOffer(offer)) {
				retOffer = offersDao.getOfferById(offer.getId());
			}
		} catch (DataAccessException ex) {

		}
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
		ApplicationContext context = new ClassPathXmlApplicationContext(DaoContextXmlUrl);
		OffersDAO offersDao = (OffersDAO) context.getBean("offersDao");
		try {
			offersDao.createOffers(offers);
		} catch (DataAccessException ex) {

		}
		((ClassPathXmlApplicationContext) context).close();
		return offers;
	}

	/**
	 * Mass update
	 * 
	 * @param offers
	 * @return
	 */
	@RequestMapping(value = "/users/massupdate", method = RequestMethod.POST)
	public @ResponseBody List<Offer> put(@RequestBody List<Offer> offers) {
		ApplicationContext context = new ClassPathXmlApplicationContext(DaoContextXmlUrl);
		OffersDAO offersDao = (OffersDAO) context.getBean("offersDao");
		try {
			offersDao.updateOffers(offers);
		} catch (DataAccessException ex) {

		}
		((ClassPathXmlApplicationContext) context).close();
		return offers;
	}
}
