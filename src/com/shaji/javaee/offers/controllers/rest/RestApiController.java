package com.shaji.javaee.offers.controllers.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shaji.javaee.offers.controllers.exceptions.DatabaseErrorException;
import com.shaji.javaee.offers.controllers.exceptions.InvalidLoginException;
import com.shaji.javaee.offers.controllers.exceptions.InvalidPayloadException;
import com.shaji.javaee.offers.controllers.exceptions.RecordNotFoundException;
import com.shaji.javaee.offers.model.Offer;
import com.shaji.javaee.offers.model.OffersDAO;

@RestController
@RequestMapping(value = "/v1")
public class RestApiController {

	@Autowired
	private OffersDAO offersDao;

	/**
	 * Get all
	 * 
	 * @return
	 * @throws DatabaseErrorException
	 * @throws InvalidLoginException
	 */
	@RequestMapping(value = "/offers", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Offer>> get(
			@RequestHeader(name = "access_token", required = false, defaultValue = "") String accessToken,
			@RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
			@RequestParam(name = "limit", required = false, defaultValue = "25") int limit,
			@RequestParam(name = "search", required = false, defaultValue = "") String searchString)
					throws DatabaseErrorException, InvalidLoginException {
		if (!isValidLogin(accessToken)) {
			throw new InvalidLoginException();
		}
		try {
			List<Offer> retOffers = offersDao.getOffers(offset, limit, searchString);
			return new ResponseEntity<List<Offer>>(retOffers, HttpStatus.OK);
		} catch (DataAccessException ex) {
			throw new DatabaseErrorException(ex);
		}
	}

	/**
	 * Get by id
	 * 
	 * @param id
	 * @return
	 * @throws RecordNotFoundException
	 * @throws DatabaseErrorException
	 * @throws InvalidLoginException
	 */
	@RequestMapping(value = "/offers/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Offer> getById(@RequestHeader(name = "access_token", required = false, defaultValue = "") String accessToken,
			@PathVariable("id") int id) throws RecordNotFoundException, DatabaseErrorException, InvalidLoginException {
		if (!isValidLogin(accessToken)) {
			throw new InvalidLoginException();
		}
		try {
			Offer retOffer = offersDao.getOfferById(id);
			if (retOffer != null) {
				return new ResponseEntity<Offer>(retOffer, HttpStatus.OK);
			} else {
				throw new RecordNotFoundException();
			}
		} catch (DataAccessException ex) {
			throw new DatabaseErrorException(ex);
		}
	}

	/**
	 * Create one
	 * 
	 * @param user
	 * @return
	 * @throws DatabaseErrorException
	 * @throws InvalidLoginException
	 * @throws InvalidPayloadException
	 */
	@RequestMapping(value = "/offers", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Offer> create(@RequestHeader(name = "access_token", required = false, defaultValue = "") String accessToken,
			@RequestBody @Valid Offer offer, BindingResult result) throws DatabaseErrorException, InvalidLoginException, InvalidPayloadException {
		if (!isValidLogin(accessToken)) {
			throw new InvalidLoginException();
		}
		if (result.hasErrors()) {
			throw new InvalidPayloadException(result);
		}
		try {
			int retId = offersDao.createOffer(offer);
			Offer retOffer = offersDao.getOfferById(retId);
			return new ResponseEntity<Offer>(retOffer, HttpStatus.CREATED);
		} catch (DataAccessException ex) {
			throw new DatabaseErrorException(ex);
		}
	}

	/**
	 * Update one
	 * 
	 * @param user
	 * @return
	 * @throws DatabaseErrorException
	 * @throws RecordNotFoundException
	 * @throws InvalidLoginException
	 * @throws InvalidPayloadException
	 */
	@RequestMapping(value = "/offers/{id}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Offer> update(@RequestHeader(name = "access_token", required = false, defaultValue = "") String accessToken,
			@PathVariable("id") int id, @RequestBody @Valid Offer offer, BindingResult result)
					throws DatabaseErrorException, RecordNotFoundException, InvalidLoginException, InvalidPayloadException {
		if (!isValidLogin(accessToken)) {
			throw new InvalidLoginException();
		}
		if (result.hasErrors()) {
			throw new InvalidPayloadException(result);
		}
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
		}
	}

	/**
	 * Delete an offer
	 * 
	 * @param id
	 * @return
	 * @throws DatabaseErrorException
	 * @throws RecordNotFoundException
	 * @throws InvalidLoginException
	 */
	@RequestMapping(value = "/offers/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<String> delete(@RequestHeader(name = "access_token", required = false, defaultValue = "") String accessToken,
			@PathVariable("id") int id) throws DatabaseErrorException, RecordNotFoundException, InvalidLoginException {
		if (!isValidLogin(accessToken)) {
			throw new InvalidLoginException();
		}
		try {
			if (offersDao.deleteOffer(id)) {
				return new ResponseEntity<String>("success", HttpStatus.NO_CONTENT);
			} else {
				throw new RecordNotFoundException();
			}
		} catch (DataAccessException ex) {
			throw new DatabaseErrorException(ex);
		}
	}

	/**
	 * Mass create
	 * 
	 * @param users
	 * @return
	 * @throws DatabaseErrorException
	 * @throws InvalidLoginException
	 */
	@RequestMapping(value = "/offers/masscreate", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> massCreate(
			@RequestHeader(name = "access_token", required = false, defaultValue = "") String accessToken, @RequestBody List<Offer> offers)
					throws DatabaseErrorException, InvalidLoginException {
		if (!isValidLogin(accessToken)) {
			throw new InvalidLoginException();
		}
		try {
			offersDao.createOffers(offers);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (DataAccessException ex) {
			throw new DatabaseErrorException(ex);
		}
	}

	/**
	 * Mass update
	 * 
	 * @param offers
	 * @return
	 * @throws DatabaseErrorException
	 * @throws InvalidLoginException
	 */
	@RequestMapping(value = "/offers/massupdate", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<String> massUpdate(
			@RequestHeader(name = "access_token", required = false, defaultValue = "") String accessToken, @RequestBody List<Offer> offers)
					throws DatabaseErrorException, InvalidLoginException {
		if (!isValidLogin(accessToken)) {
			throw new InvalidLoginException();
		}
		try {
			offersDao.updateOffers(offers);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (DataAccessException ex) {
			throw new DatabaseErrorException(ex);
		}
	}

	private boolean isValidLogin(String accessToken) {
		String token = "123456789";
		if (token.equals(accessToken)) {
			return true;
		} else {
			return false;
		}
	}

	@ExceptionHandler(InvalidLoginException.class)
	private ResponseEntity<RestError> rulesForInvalidLogin(Exception e) {
		RestError restError = new RestError("Invalid login");
		return new ResponseEntity<RestError>(restError, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(InvalidPayloadException.class)
	private ResponseEntity<RestError> rulesForInvalidPayload(Exception e) {
		RestError restError = new RestError("Invalid Payload : " + e.getMessage());
		return new ResponseEntity<RestError>(restError, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RecordNotFoundException.class)
	private ResponseEntity<RestError> rulesForRecordNotFound(Exception e) {
		RestError restError = new RestError("Record not found : " + e.getMessage());
		return new ResponseEntity<RestError>(restError, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DatabaseErrorException.class)
	private ResponseEntity<RestError> rulesForDatabaseError(Exception e) {
		RestError restError = new RestError("Database Error : " + e.getMessage());
		return new ResponseEntity<RestError>(restError, HttpStatus.BAD_REQUEST);
	}
}
