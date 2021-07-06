package com.mind.bowser.controller;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mind.bowser.dto.ResponseDto;
import com.mind.bowser.dto.UserDto;
import com.mind.bowser.entity.User;
import com.mind.bowser.services.UserService;

@CrossOrigin
@RestController
@RequestMapping("/mindbowser/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/sign-up")
	public ResponseDto<User> signUp(@RequestBody @Valid UserDto userDto, HttpServletResponse httpServletResponse) {

		ResponseDto<User> responseDto = new ResponseDto<User>();
		if (Objects.isNull(userService.findByEmail(userDto.getEmail()))
				&& Objects.isNull(userService.findByMobile(userDto.getMobile()))) {
			User user = userService.signUp(userDto);
			httpServletResponse.setStatus(HttpStatus.CREATED.value());
			responseDto.setStatusCode(HttpStatus.CREATED.value());
			responseDto.setMessage("USER REGISTER SUCCESSFULLY");
			responseDto.setData(user);
		} else {
			httpServletResponse.setStatus(HttpStatus.CONFLICT.value());
			responseDto.setStatusCode(HttpStatus.CONFLICT.value());
			responseDto.setMessage("EMAIL OR MOBILE ALREADY EXIST");
		}
		return responseDto;
	}

	@PostMapping("/sign-in")
	public ResponseDto<User> signIn(@RequestBody @Valid UserDto userDto, HttpServletResponse httpServletResponse) {

		ResponseDto<User> responseDto = new ResponseDto<User>();
		User user = userService.findByMobile(userDto.getMobile());
		if (Objects.nonNull(user)) {
			httpServletResponse.setStatus(HttpStatus.OK.value());
			responseDto.setStatusCode(HttpStatus.OK.value());
			user = userService.signIn(user);
			responseDto.setMessage("USER LOGGED IN SUCCESSFULLY");
			responseDto.setData(user);
		} else {
			httpServletResponse.setStatus(HttpStatus.NOT_FOUND.value());
			responseDto.setStatusCode(HttpStatus.NOT_FOUND.value());
			responseDto.setMessage("MOBILE NUMBER NOT PRESESNT");
		}
		return responseDto;
	}

	@GetMapping("/emailId")
	public ResponseDto<User> getUserByEmailId(@RequestParam(value = "emailId") String emailId,
			HttpServletResponse httpServletResponse) {

		ResponseDto<User> responseDto = new ResponseDto<User>();
		if (Objects.nonNull(emailId)) {
			User user = userService.findByEmail(emailId);
			httpServletResponse.setStatus(HttpStatus.OK.value());
			responseDto.setStatusCode(HttpStatus.OK.value());
			responseDto.setMessage("RECORD FETCHED SUCCESSFULLY");
			responseDto.setData(user);
		} else {
			httpServletResponse.setStatus(HttpStatus.NOT_FOUND.value());
			responseDto.setStatusCode(HttpStatus.NOT_FOUND.value());
			responseDto.setMessage("EMAIL OR MOBILE ALREADY EXIST");
		}
		return responseDto;
	}

	@GetMapping("/list")
	public ResponseDto<List<User>> list(HttpServletResponse httpServletResponse) {

		ResponseDto<List<User>> responseDto = new ResponseDto<>();
		List<User> dealers = userService.listOfUser();
		httpServletResponse.setStatus(HttpStatus.OK.value());
		responseDto.setStatusCode(HttpStatus.OK.value());
		responseDto.setMessage("RECORD FETCHED SUCCESSFULLY");
		responseDto.setData(dealers);
		return responseDto;
	}

	@GetMapping("/id")
	public ResponseDto<User> list(@RequestParam(value = "id") Long id, HttpServletResponse httpServletResponse) {

		ResponseDto<User> responseDto = new ResponseDto<>();
		User dealers = userService.findById(id);
		httpServletResponse.setStatus(HttpStatus.OK.value());
		responseDto.setStatusCode(HttpStatus.OK.value());
		responseDto.setMessage("RECORD FETCHED SUCCESSFULLY");
		responseDto.setData(dealers);

		return responseDto;
	}

}
