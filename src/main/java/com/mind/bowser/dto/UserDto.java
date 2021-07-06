package com.mind.bowser.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.mind.bowser.entity.Address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserDto {

	private Long id;
	private String name;

	@Email
	@NotEmpty
	private String email;
	@NotEmpty
	private String password;

	private String newPassword;
	private String mobile;
	private String otp;
	private Address address;
	private String type;
	private String provider;
}
