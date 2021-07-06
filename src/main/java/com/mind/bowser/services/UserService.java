package com.mind.bowser.services;

import java.util.List;

import com.mind.bowser.dto.UserDto;
import com.mind.bowser.entity.User;

public interface UserService {

	User signUp(UserDto userDto);

	User findByEmail(String emailId);

	User findByMobile(String mobile);

	List<User> listOfUser();

	User findById(Long id);

	User signIn(User user);

}
