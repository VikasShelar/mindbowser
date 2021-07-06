package com.mind.bowser.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mind.bowser.utility.CommonUtils;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@Autowired
	private CommonUtils commonUtils;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> details = new ArrayList<>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			details.add(error.getDefaultMessage());
		}
		return new ResponseEntity<Object>(commonUtils.setData(HttpStatus.BAD_REQUEST.value(), details.toString(), null),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = UnauthorizedException.class)
	public ResponseEntity<Object> UnauthorizedException(UnauthorizedException ex, WebRequest request) {
		return new ResponseEntity<Object>(commonUtils.setData(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), null),
				HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(RuntimeException.class)
	public final ResponseEntity<Object> runtimeException(RuntimeException ex, WebRequest request) {
		return new ResponseEntity<Object>(
				commonUtils.setData(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(value = JwtException.class)
	public ResponseEntity<?> jwtException(JwtException ex, WebRequest request) {
		return new ResponseEntity<>(commonUtils.setData(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), null),
				HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(value = UserException.class)
	public ResponseEntity<Object> userException(UserException ex, WebRequest request) {
		return new ResponseEntity<Object>(
				commonUtils.setData(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = NotFoundException.class)
	public ResponseEntity<Object> notFoundException(NotFoundException ex, WebRequest request) {
		return new ResponseEntity<Object>(commonUtils.setData(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = ForbiddenException.class)
	public ResponseEntity<Object> forbiddenException(ForbiddenException ex, WebRequest request) {
		return new ResponseEntity<Object>(commonUtils.setData(HttpStatus.FORBIDDEN.value(), ex.getMessage(), null),
				HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(value = InsufficientAuthenticationException.class)
	public ResponseEntity<Object> InsufficientAuthenticationException(InsufficientAuthenticationException ex,
			WebRequest request) {
		return new ResponseEntity<Object>(commonUtils.setData(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), null),
				HttpStatus.UNAUTHORIZED);
	}

}
