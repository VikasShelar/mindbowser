package com.mind.bowser.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mind.bowser.dto.EmployeeDto;
import com.mind.bowser.dto.UserDto;
import com.mind.bowser.entity.Employee;
import com.mind.bowser.entity.User;
import com.mind.bowser.repo.EmployeeRepository;
import com.mind.bowser.repo.UserRepository;
import com.mind.bowser.services.EmployeeService;
import com.mind.bowser.utility.CommonUtils;

import io.jsonwebtoken.Header;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {


	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	CommonUtils commonUtils;

	@Override
	public Employee saveEmployee(UserDto userDto, EmployeeDto employeeDto) {
		Employee emp=new Employee(0l, employeeDto.getFirstName(), employeeDto.getLastName(),
				employeeDto.getAddress(), employeeDto.getDob(), employeeDto.getMobile(),
				employeeDto.getCity());
				return employeeRepository.save(emp);
	}


	//ResponseDto<List<PurchaseInvoiceDto>>

	public EmployeeDto findById(Long empId) {
		Optional<Employee> employee = employeeRepository.findById(empId);
		return buildEmployeeDto(employee.get());
//		List<EmployeeDto> lstEmployeeDtoDto = employee.get()
//				.map(emp -> modelMapper.map(emp, EmployeeDto.class)).collect(Collectors.toList());
//		return lstEmployeeDtoDto;
	}

	private EmployeeDto buildEmployeeDto(Employee employee) {
		EmployeeDto empDto=new EmployeeDto();
		empDto.setAddress(employee.getAddress());
		empDto.setCity(employee.getCity());
empDto.setDob(employee.getDob());
empDto.setEmpId(employee.getEmpId());
empDto.setFirstName(employee.getFirstName());
empDto.setLastName(employee.getLastName());
empDto.setMobile(employee.getMobile());
			return empDto;	
	}


	
	public List<EmployeeDto> findAll() {
		List<Employee> lstEmployee = employeeRepository.findAll();
		List<EmployeeDto> listEmployeeDtos = new ArrayList<>();
		for (Employee e : lstEmployee) {
			EmployeeDto salesQuotationDto = buildEmployeeDto(e);
			listEmployeeDtos.add(salesQuotationDto);
		}
		return listEmployeeDtos;
	}

	
	public EmployeeDto update(@Valid EmployeeDto employeeDto, UserDto userDto) {
		Optional<User> user = userRepository.findById(userDto.getId());
		if (employeeDto.getEmpId() == null || employeeDto.getEmpId() == 0) {
			return null;
		}
		
		Employee emp=new Employee(0l, employeeDto.getFirstName(), employeeDto.getLastName(),
				employeeDto.getAddress(), employeeDto.getDob(), employeeDto.getMobile(),
				employeeDto.getCity());
		emp = employeeRepository.save(emp);
		return modelMapper.map(emp, EmployeeDto.class);
	}

	@Override
	public void delete(Long id) {
		Optional<Employee> emp = employeeRepository.findById(id);
		if (emp.isPresent()) {
			employeeRepository.delete(emp.get());
		}
		
	}
}
