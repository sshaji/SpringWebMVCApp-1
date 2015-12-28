package com.shaji.javaee.offers.controllers.exceptions;

public class InvalidPayloadException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidPayloadException(String error) {
		super(error);
	}
}
