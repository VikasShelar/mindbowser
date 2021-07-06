package com.mind.bowser.services;

import java.util.List;

import javax.validation.Valid;

import com.mind.bowser.dto.EmployeeDto;
import com.mind.bowser.dto.UserDto;
import com.mind.bowser.entity.Employee;

public interface EmployeeService {

	Employee saveEmployee(UserDto userDto, EmployeeDto employeeDto);

	EmployeeDto findById(Long empId);

	List<EmployeeDto> findAll();

	EmployeeDto update(@Valid EmployeeDto employeeDto, UserDto userDto);

	void delete(Long id);

}