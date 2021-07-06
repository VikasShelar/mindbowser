package com.mind.bowser.dto;

import java.time.LocalDate;

import javax.validation.constraints.Pattern;

import com.mind.bowser.entity.Employee;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
private Long empId;
	
	private String firstName;
	private String lastName;
	private String address;
    private LocalDate dob;
	@Pattern(regexp="(^$|[0-9]{10})")
	@NotNull
	private String mobile;
	private String city;
	
}
