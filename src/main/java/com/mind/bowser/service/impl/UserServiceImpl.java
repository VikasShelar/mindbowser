package com.mind.bowser.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mind.bowser.dto.UserDto;
import com.mind.bowser.entity.JwtToken;
import com.mind.bowser.entity.Role;
import com.mind.bowser.entity.User;
import com.mind.bowser.exception.NotFoundException;
import com.mind.bowser.repo.RoleRepository;
import com.mind.bowser.repo.UserRepository;
import com.mind.bowser.services.UserService;
import com.mind.bowser.utility.JwtProvider;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private JwtTokenRepository jwtTokenRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	@Override
	public User signUp(UserDto userDto) {
		Set<Role> roles = new HashSet<Role>();
		try {
			if (Objects.isNull(userDto.getPassword()))
				userDto.setPassword(passwordEncoder.encode("DEFAULT PASSWORD"));
			User user = new User(userDto.getId(), userDto.getName(), userDto.getEmail(), userDto.getMobile(),
					userDto.getPassword());
			Optional<Role> role = roleRepository.findByType(userDto.getType());
			if (!role.isPresent())
				roles.add(new Role(userDto.getType()));
			else
				roles.add(role.get());

			user.setRoles(roles);
			user = userRepository.save(user);
			user.setJwtToken(new JwtToken(jwtProvider.generateJwtToken(user), user));
			return user;
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public User findByEmail(String emailId) {

		return userRepository.findByEmail(emailId);
	}

	@Override
	public User findByMobile(String mobile) {

		return userRepository.findByMobile(mobile);
	}

	@Override
	public List<User> listOfUser() {
		List<User> users = userRepository.findAll();
		List<User> userList = users.stream().filter(
				x -> x.getRoles().parallelStream().anyMatch(role -> role.getType().equalsIgnoreCase("EMPLOYEE")))
				.collect(Collectors.toList());
		return userList;
	}

	@Override
	public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(emailId);
		if (!Objects.isNull(user)) {
			if (null == user.getPassword()) {
				return new org.springframework.security.core.userdetails.User(user.getEmail(), "", new ArrayList<>());
			}
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
					new ArrayList<>());
		} else
			throw new UsernameNotFoundException("User Not Found with email : " + emailId);

	}

	@Override
	public User findById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent())
			return user.get();
		else
			throw new NotFoundException("UserId Not Found : " + id);
	}

	@Override
	public User signIn(User user) {
		try {

			JwtToken jwtToken = updateJwtToken(user);
			user.getJwtToken().setAccessToken(jwtToken.getAccessToken());
			return user;
		} catch (Exception e) {
			throw e;
		}
	}

	private JwtToken updateJwtToken(User user) {
		JwtToken jwtToken = jwtTokenRepository.findByUserId(user.getId());
		String jwtTokenNew = jwtProvider.generateJwtToken(user);
		jwtToken.setAccessToken(jwtTokenNew);
		jwtTokenRepository.save(jwtToken);
		return jwtToken;
	}

}
