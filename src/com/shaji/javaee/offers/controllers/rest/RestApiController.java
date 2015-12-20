package com.shaji.javaee.offers.controllers.rest;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shaji.javaee.offers.controllers.exceptions.DatabaseErrorException;
import com.shaji.javaee.offers.controllers.exceptions.RecordNotFoundException;
import com.shaji.javaee.offers.controllers.exceptions.RestError;
import com.shaji.javaee.offers.model.Offer;
import com.shaji.javaee.offers.model.OffersDAO;

@RestController
@RequestMapping(value = "/v1")
public class RestApiController {

	private String DaoContextXmlUrl = "classpath:com/shaji/javaee/offers/config/dao-context.xml";

	/**
	 * Get all
	 * 
	 * @return
	 * @throws DatabaseErrorException
	 */
	@RequestMapping(value = "/offers", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Offer>> get(
			@RequestParam(name = "limit", required = false, defaultValue = "25") int limit)
					throws DatabaseErrorException {
		ApplicationContext context = new ClassPathXmlApplicationContext(DaoContextXmlUrl);
		OffersDAO offersDao = (OffersDAO) context.getBean("offersDao");
		List<Offer> retOffers = null;
		try {
			retOffers = offersDao.getOffers(limit);
			return new ResponseEntity<List<Offer>>(retOffers, HttpStatus.OK);
		} catch (DataAccessException ex) {
			throw new DatabaseErrorException(ex);
		} finally {
			((ClassPathXmlApplicationContext) context).close();
		}
	}

	/**
	 * Get by id
	 * 
	 * @param id
	 * @return
	 * @throws RecordNotFoundException
	 * @throws DatabaseErrorException
	 */
	@RequestMapping(value = "/offers/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Offer> getById(@PathVariable("id") int id)
			throws RecordNotFoundException, DatabaseErrorException {
		ApplicationContext context = new ClassPathXmlApplicationContext(DaoContextXmlUrl);
		OffersDAO offersDao = (OffersDAO) context.getBean("offersDao");
		try {
			Offer retOffer = offersDao.getOfferById(id);
			if (retOffer != null) {
				return new ResponseEntity<Offer>(retOffer, HttpStatus.OK);
			} else {
				throw new RecordNotFoundException();
			}
		} catch (DataAccessException ex) {
			throw new DatabaseErrorException(ex);
		} finally {
			((ClassPathXmlApplicationContext) context).close();
		}
	}

	/**
	 * Create one
	 * 
	 * @param user
	 * @return
	 * @throws DatabaseErrorException
	 */
	@RequestMapping(value = "/offers", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Offer> create(@RequestBody Offer offer) throws DatabaseErrorException {
		ApplicationContext context = new ClassPathXmlApplicationContext(DaoContextXmlUrl);
		OffersDAO offersDao = (OffersDAO) context.getBean("offersDao");
		try {
			int retId = offersDao.createOffer(offer);
			Offer retOffer = offersDao.getOfferById(retId);
			return new ResponseEntity<Offer>(retOffer, HttpStatus.CREATED);
		} catch (DataAccessException ex) {
			throw new DatabaseErrorException(ex);
		} finally {
			((ClassPathXmlApplicationContext) context).close();
		}
	}

	/**
	 * Update one
	 * 
	 * @param user
	 * @return
	 * @throws DatabaseErrorException
	 * @throws RecordNotFoundException
	 */
	@RequestMapping(value = "/offers/{id}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Offer> update(@PathVariable("id") int id, @RequestBody Offer offer)
			throws DatabaseErrorException, RecordNotFoundException {
		ApplicationContext context = new ClassPathXmlApplicationContext(DaoContextXmlUrl);
		OffersDAO offersDao = (OffersDAO) context.getBean("offersDao");
		try {
			offer.setId(id);
			if (offersDao.updateOffer(offer)) {
				Offer retOffer = offersDao.getOfferById(offer.getId());
				return new ResponseEntity<Offer>(retOffer, HttpStatus.OK);
			} else {
				throw new RecordNotFoundException();
			}
		} catch (DataAccessException ex) {
			throw new DatabaseErrorException(ex);
		} finally {
			((ClassPathXmlApplicationContext) context).close();
		}
	}

	/**
	 * Delete an offer
	 * 
	 * @param id
	 * @return
	 * @throws DatabaseErrorException
	 * @throws RecordNotFoundException
	 */
	@RequestMapping(value = "/offers/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<String> delete(@PathVariable("id") int id)
			throws DatabaseErrorException, RecordNotFoundException {
		ApplicationContext context = new ClassPathXmlApplicationContext(DaoContextXmlUrl);
		OffersDAO offersDao = (OffersDAO) context.getBean("offersDao");
		try {
			if (offersDao.deleteOffer(id)) {
				return new ResponseEntity<String>("success", HttpStatus.NO_CONTENT);
			} else {
				throw new RecordNotFoundException();
			}
		} catch (DataAccessException ex) {
			throw new DatabaseErrorException(ex);
		} finally {
			((ClassPathXmlApplicationContext) context).close();
		}
	}

	/**
	 * Mass create
	 * 
	 * @param users
	 * @return
	 * @throws DatabaseErrorException
	 */
	@RequestMapping(value = "/offers/masscreate", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> massCreate(@RequestBody List<Offer> offers)
			throws DatabaseErrorException {
		ApplicationContext context = new ClassPathXmlApplicationContext(DaoContextXmlUrl);
		OffersDAO offersDao = (OffersDAO) context.getBean("offersDao");
		try {
			offersDao.createOffers(offers);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (DataAccessException ex) {
			throw new DatabaseErrorException(ex);
		} finally {
			((ClassPathXmlApplicationContext) context).close();
		}
	}

	/**
	 * Mass update
	 * 
	 * @param offers
	 * @return
	 * @throws DatabaseErrorException
	 */
	@RequestMapping(value = "/offers/massupdate", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<String> massUpdate(@RequestBody List<Offer> offers)
			throws DatabaseErrorException {
		ApplicationContext context = new ClassPathXmlApplicationContext(DaoContextXmlUrl);
		OffersDAO offersDao = (OffersDAO) context.getBean("offersDao");
		try {
			offersDao.updateOffers(offers);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (DataAccessException ex) {
			throw new DatabaseErrorException(ex);
		} finally {
			((ClassPathXmlApplicationContext) context).close();
		}
	}

	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<RestError> rulesForRecordNotFound() {
		RestError restError = new RestError("Record not found");
		return new ResponseEntity<RestError>(restError, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DatabaseErrorException.class)
	public ResponseEntity<RestError> rulesForDatabaseError(Exception e) {
		RestError restError = new RestError(e.getCause().toString());
		return new ResponseEntity<RestError>(restError, HttpStatus.BAD_REQUEST);
	}
}
