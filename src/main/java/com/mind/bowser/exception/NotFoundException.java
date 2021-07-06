package com.mind.bowser.exception;

@SuppressWarnings("serial")
public class NotFoundException extends RuntimeException {

	public NotFoundException(String message) {
		super(message);
	}

}
