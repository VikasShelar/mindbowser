package com.mind.bowser.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mind.bowser.entity.Authorization;

@Repository
public interface AuthorizationRepository extends JpaRepository<Authorization, Long> {

	Optional<Authorization> findByUrl(String url);

}
