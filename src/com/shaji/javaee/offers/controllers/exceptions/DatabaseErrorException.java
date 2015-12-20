package com.shaji.javaee.offers.controllers.exceptions;

import org.springframework.dao.DataAccessException;

public class DatabaseErrorException extends Exception {

	public DatabaseErrorException(DataAccessException ex) {
		super(ex);
	}

	private static final long serialVersionUID = 1L;

}
