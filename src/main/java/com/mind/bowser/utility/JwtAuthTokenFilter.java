package com.mind.bowser.utility;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.mind.bowser.common.InMemoryData;
import com.mind.bowser.entity.User;
import com.mind.bowser.exception.ForbiddenException;
import com.mind.bowser.exception.JwtException;
import com.mind.bowser.exception.UnauthorizedException;
import com.mind.bowser.service.impl.UserServiceImpl;

@Component
public class JwtAuthTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtProvider tokenProvider;

	@Autowired
	private UserServiceImpl userDetailsService;

	@Autowired
	private InMemoryData inMemoryData;

	@Autowired
	@Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver resolver;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		User user = null;
		try {
			String jwt = getJwt(request);

			if (jwt != null && tokenProvider.validateJwtToken(jwt)) {
				String userId = tokenProvider.getUserIdFromJwtToken(jwt);
				user = userDetailsService.findById(Long.parseLong(userId));
				UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				String roles = inMemoryData.getRole(request.getRequestURI());
				String ids = user.getRoles().stream().map(a -> String.valueOf(a.getId()))
						.collect(Collectors.joining(","));
				if (checkUserAuthorization(roles, ids))
					filterChain.doFilter(request, response);
				else
					throw new ForbiddenException("ACCESS_DENIED");
			} else {
				filterChain.doFilter(request, response);
			}

		} catch (UnauthorizedException e) {
			resolver.resolveException(request, response, null, e);
		} catch (JwtException e) {
			resolver.resolveException(request, response, null, e);
		} catch (Exception e) {
			resolver.resolveException(request, response, null, e);
		}
	}

	private String getJwt(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");

		if (authHeader != null && authHeader.startsWith("Bearer")) {
			return authHeader.replace("Bearer", "");
		}

		return null;
	}

	private Boolean checkUserAuthorization(String roles, String ids) {
		if (Objects.nonNull(ids) && Objects.nonNull(roles)) {
			if (roles.contains(ids))
				return Boolean.TRUE;
			else
				return Boolean.FALSE;
		} else
			throw new UnauthorizedException("URL not assign please contact Administrator");
	}
}