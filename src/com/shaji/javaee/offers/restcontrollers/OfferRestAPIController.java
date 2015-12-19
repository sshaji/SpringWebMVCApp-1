package com.shaji.javaee.offers.restcontrollers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	@RequestMapping(value = "/offers", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Offer>> get(
			@RequestParam(name = "limit", required = false, defaultValue = "25") int limit) {
		ApplicationContext context = new ClassPathXmlApplicationContext(DaoContextXmlUrl);
		OffersDAO offersDao = (OffersDAO) context.getBean("offersDao");
		List<Offer> retOffers = null;
		try {
			retOffers = offersDao.getOffers(limit);
		} catch (DataAccessException ex) {
			return new ResponseEntity<List<Offer>>(new ArrayList<Offer>(), HttpStatus.BAD_REQUEST);
		} finally {
			((ClassPathXmlApplicationContext) context).close();
		}
		return new ResponseEntity<List<Offer>>(retOffers, HttpStatus.OK);
	}

	/**
	 * Get by id
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/offers/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Offer> getById(@PathVariable("id") int id) {
		ApplicationContext context = new ClassPathXmlApplicationContext(DaoContextXmlUrl);
		OffersDAO offersDao = (OffersDAO) context.getBean("offersDao");
		Offer retOffer = null;
		try {
			retOffer = offersDao.getOfferById(id);
		} catch (DataAccessException ex) {
			return new ResponseEntity<Offer>(new Offer(), HttpStatus.BAD_REQUEST);
		} finally {
			((ClassPathXmlApplicationContext) context).close();
		}
		return new ResponseEntity<Offer>(retOffer, HttpStatus.OK);
	}

	/**
	 * Create one
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/offers", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Offer> create(@RequestBody Offer offer) {
		ApplicationContext context = new ClassPathXmlApplicationContext(DaoContextXmlUrl);
		OffersDAO offersDao = (OffersDAO) context.getBean("offersDao");
		Offer retOffer = null;
		try {
			int retId = offersDao.createOffer(offer);
			if (retId != 0) {
				retOffer = offersDao.getOfferById(retId);
			}
		} catch (DataAccessException ex) {
			return new ResponseEntity<Offer>(new Offer(), HttpStatus.BAD_REQUEST);
		} finally {
			((ClassPathXmlApplicationContext) context).close();
		}
		return new ResponseEntity<Offer>(retOffer, HttpStatus.OK);
	}

	/**
	 * Update one
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/offers/{id}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Offer> update(@PathVariable("id") int id, @RequestBody Offer offer) {
		ApplicationContext context = new ClassPathXmlApplicationContext(DaoContextXmlUrl);
		OffersDAO offersDao = (OffersDAO) context.getBean("offersDao");
		Offer retOffer = null;
		try {
			offer.setId(id);
			if (offersDao.updateOffer(offer)) {
				retOffer = offersDao.getOfferById(offer.getId());
			}
		} catch (DataAccessException ex) {
			return new ResponseEntity<Offer>(new Offer(), HttpStatus.BAD_REQUEST);
		} finally {
			((ClassPathXmlApplicationContext) context).close();
		}
		return new ResponseEntity<Offer>(retOffer, HttpStatus.OK);
	}

	/**
	 * Delete an offer
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/offers/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<String> delete(@PathVariable("id") int id) {
		ApplicationContext context = new ClassPathXmlApplicationContext(DaoContextXmlUrl);
		OffersDAO offersDao = (OffersDAO) context.getBean("offersDao");
		try {
			offersDao.deleteOffer(id);
		} catch (DataAccessException ex) {
			return new ResponseEntity<String>("failure", HttpStatus.BAD_REQUEST);
		} finally {
			((ClassPathXmlApplicationContext) context).close();
		}
		return new ResponseEntity<String>("success", HttpStatus.NO_CONTENT);
	}

	/**
	 * Mass create
	 * 
	 * @param users
	 * @return
	 */
	@RequestMapping(value = "/offers/masscreate", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> massCreate(@RequestBody List<Offer> offers) {
		ApplicationContext context = new ClassPathXmlApplicationContext(DaoContextXmlUrl);
		OffersDAO offersDao = (OffersDAO) context.getBean("offersDao");
		try {
			offersDao.createOffers(offers);
		} catch (DataAccessException ex) {
			return new ResponseEntity<String>("failure", HttpStatus.BAD_REQUEST);
		} finally {
			((ClassPathXmlApplicationContext) context).close();
		}
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}

	/**
	 * Mass update
	 * 
	 * @param offers
	 * @return
	 */
	@RequestMapping(value = "/offers/massupdate", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<String> massUpdate(@RequestBody List<Offer> offers) {
		ApplicationContext context = new ClassPathXmlApplicationContext(DaoContextXmlUrl);
		OffersDAO offersDao = (OffersDAO) context.getBean("offersDao");
		try {
			offersDao.updateOffers(offers);
		} catch (DataAccessException ex) {
			return new ResponseEntity<String>("failure", HttpStatus.BAD_REQUEST);
		} finally {
			((ClassPathXmlApplicationContext) context).close();
		}
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
}
