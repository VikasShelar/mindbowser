package com.mind.bowser.utility;

import java.security.SignatureException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.mind.bowser.entity.User;
import com.mind.bowser.exception.JwtException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider {

	@Value("${mindbowser.secret-key}")
	private String secretKey;

	@Value("${mindbowser.token-expiry}")
	private Long tokenExpiryTime;

	public String generateJwtToken(User user) {

		return Jwts.builder().setSubject(user.getId().toString()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + (tokenExpiryTime * 24 * 60 * 60 * 1000)))
				.setIssuedAt(new Date(System.currentTimeMillis())).signWith(SignatureAlgorithm.HS512, secretKey)
				.compact();
	}

	public String getUserIdFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		boolean status = false;
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
			status = true;
		} catch (MalformedJwtException e) {
			
			throw e;
		} catch (ExpiredJwtException e) {
			throw new JwtException("TOKEN EXPIRED");
		} catch (UnsupportedJwtException e) {
			throw e;
		} catch (IllegalArgumentException e) {
			throw e;
		}
		return status;
	}

	public String jwtPrinciple() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		} else {
			return principal.toString();
		}
	}
}
