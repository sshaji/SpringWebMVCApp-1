package com.shaji.javaee.controller.rest.exceptions;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class InvalidPayloadException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message;

	public InvalidPayloadException(BindingResult result) {
		StringBuilder error = new StringBuilder();

		for (ObjectError e : result.getAllErrors()) {
			if (error.length() > 0) {
				error.append(", ");
			}
			error.append(e.getDefaultMessage());
		}

		this.message = error.toString();
	}

	@Override
	public String getMessage() {
		return message;
	}

}
