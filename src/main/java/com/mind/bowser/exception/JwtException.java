package com.mind.bowser.exception;

@SuppressWarnings("serial")
public class JwtException extends RuntimeException {
	public JwtException(String message) {
		super(message);
	}
}
