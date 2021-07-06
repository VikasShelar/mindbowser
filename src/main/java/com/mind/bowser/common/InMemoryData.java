package com.mind.bowser.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mind.bowser.entity.Authorization;
import com.mind.bowser.repo.AuthorizationRepository;

@Component
public class InMemoryData {

	private static AuthorizationRepository authorizationRepository;
	private static Map<String, String> authorizationUrls = new HashMap<>();

	@Autowired
	public InMemoryData(AuthorizationRepository authorizationRepository) {
		InMemoryData.authorizationRepository = authorizationRepository;
	}

	public String getRole(String url) {
		return authorizationUrls.get(url);

	}

	public void setloadAuthorizationUrl(String url, String roles) {
		authorizationUrls.put(url, roles);
	}

	public void removeAuthorizationUrl(String url) {
		authorizationUrls.remove(url);
	}

	public static void loadAuthorizationUrl() {
		List<Authorization> authorizations = authorizationRepository.findAll();
		authorizationUrls = authorizations.parallelStream()
				.collect(Collectors.toMap(Authorization::getUrl, Authorization::getRole));
	}

}
