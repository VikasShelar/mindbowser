package com.mind.bowser.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mind.bowser.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

	Optional<User> findById(@Param("id") Long id);

	User findByEmail(@Param("emailId") String emailId);

	User findByMobile(@Param("mobile") String mobile);

	User findByFirstName(String userName);

}
