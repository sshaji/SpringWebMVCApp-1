package com.shaji.javaee.offers.controllers.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
import com.shaji.javaee.offers.model.form.Offer;
import com.shaji.javaee.offers.service.OffersService;

@RestController
@RequestMapping(value = "/v1")
public class RestApiOffersController {

	@Autowired
	private OffersService offersService;

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
		if (!RestApiLoginHandler.isValidLogin(accessToken)) {
			throw new InvalidLoginException();
		}
		try {
			List<Offer> retOffers = offersService.getOffers(offset, limit, searchString);
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
	public @ResponseBody ResponseEntity<Offer> getById(
			@RequestHeader(name = "access_token", required = false, defaultValue = "") String accessToken,
			@PathVariable("id") int id) throws RecordNotFoundException, DatabaseErrorException, InvalidLoginException {
		if (!RestApiLoginHandler.isValidLogin(accessToken)) {
			throw new InvalidLoginException();
		}
		try {
			Offer retOffer = offersService.getOfferById(id);
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
	public @ResponseBody ResponseEntity<Offer> create(
			@RequestHeader(name = "access_token", required = false, defaultValue = "") String accessToken,
			@RequestBody @Valid Offer offer, BindingResult result)
					throws DatabaseErrorException, InvalidLoginException, InvalidPayloadException {
		if (!RestApiLoginHandler.isValidLogin(accessToken)) {
			throw new InvalidLoginException();
		}
		if (result.hasErrors()) {
			throw new InvalidPayloadException(result);
		}
		try {
			Offer retOffer = offersService.createOffer(offer);
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
	public @ResponseBody ResponseEntity<Offer> update(
			@RequestHeader(name = "access_token", required = false, defaultValue = "") String accessToken,
			@PathVariable("id") int id, @RequestBody @Valid Offer offer, BindingResult result)
					throws DatabaseErrorException, RecordNotFoundException, InvalidLoginException,
					InvalidPayloadException {
		if (!RestApiLoginHandler.isValidLogin(accessToken)) {
			throw new InvalidLoginException();
		}
		if (result.hasErrors()) {
			throw new InvalidPayloadException(result);
		}
		try {
			if (offersService.getOfferById(id) != null) {
				Offer retOffer = offersService.updateOffer(offer);
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
	public @ResponseBody ResponseEntity<String> delete(
			@RequestHeader(name = "access_token", required = false, defaultValue = "") String accessToken,
			@PathVariable("id") int id) throws DatabaseErrorException, RecordNotFoundException, InvalidLoginException {
		if (!RestApiLoginHandler.isValidLogin(accessToken)) {
			throw new InvalidLoginException();
		}
		try {
			if (offersService.deleteOffer(id)) {
				return new ResponseEntity<String>("success", HttpStatus.NO_CONTENT);
			} else {
				throw new RecordNotFoundException();
			}
		} catch (DataAccessException ex) {
			throw new DatabaseErrorException(ex);
		}
	}

}
