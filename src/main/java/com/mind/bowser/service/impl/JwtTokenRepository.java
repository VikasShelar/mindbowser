package com.mind.bowser.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mind.bowser.entity.JwtToken;


@Repository
public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {

	JwtToken findByUserId(@Param("userId") Long userId);

	JwtToken findByAccessTokenAndUserId(String accessToken, Long id);

}
