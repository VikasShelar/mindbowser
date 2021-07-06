package com.mind.bowser.utility;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mind.bowser.dto.UserDto;
import com.mind.bowser.entity.JwtToken;
import com.mind.bowser.service.impl.JwtTokenRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenManager {

	@Value("${mindbowser.secret-key}")
	private String secretKey;

	@Value("${mindbowser.token-expiry}")
	private Long tokenExpiryTime;

	@Autowired
	private JwtTokenRepository jwtTokenRepository;

	public String getAccessToken(UserDto userDto) {

		SignatureAlgorithm algorithm = SignatureAlgorithm.HS512;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
		Key signKey = new SecretKeySpec(apiKeySecretBytes, algorithm.getJcaName());

		Map<String, Object> userDtoMap = new HashMap<>();
		userDtoMap.put("id", userDto.getId());
		userDtoMap.put("name", userDto.getName());
		userDtoMap.put("email", userDto.getEmail());
		userDtoMap.put("mobile", userDto.getMobile());

		JwtBuilder builder = Jwts.builder().claim("user", userDtoMap).setId(userDto.getId().toString()).setIssuedAt(now)
				.signWith(algorithm, signKey);
		if (tokenExpiryTime > 0) {
			long expMillis = nowMillis + tokenExpiryTime;
			Date expiry = new Date(expMillis);
			builder.setExpiration(expiry);
		}

		return builder.compact();
	}

	public UserDto getUserDto(String accessToken) {
		UserDto dto = null;

		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
				.parseClaimsJws(accessToken).getBody();
		if (claims.containsKey("user")) {
			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.convertValue(claims.get("user", LinkedHashMap.class), UserDto.class);
			JwtToken jwtToken=jwtTokenRepository.findByAccessTokenAndUserId(accessToken, dto.getId());
			if (jwtToken == null) {
				dto = null;
			}
		}
		return dto;
	}
}
