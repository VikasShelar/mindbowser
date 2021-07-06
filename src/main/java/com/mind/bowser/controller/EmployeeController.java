package com.mind.bowser.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mind.bowser.common.AppsConstant;
import com.mind.bowser.dto.EmployeeDto;
import com.mind.bowser.dto.Header;
import com.mind.bowser.dto.ResponseDto;
import com.mind.bowser.dto.UserDto;
import com.mind.bowser.entity.Employee;
import com.mind.bowser.exception.UserException;
import com.mind.bowser.repo.EmployeeRepository;
import com.mind.bowser.services.EmployeeService;
import com.mind.bowser.services.UserService;
import com.mind.bowser.utility.JwtTokenManager;

@RestController
@CrossOrigin
@RequestMapping("/api/employee")
public class EmployeeController {

	@Autowired
	UserService userservice;
	
	@Autowired
    EmployeeRepository  employeeRepository;

	@Autowired
	JwtTokenManager jwtTokenManager;
	

	@Autowired
	EmployeeService employeeService;

	private final Logger logger = LogManager.getLogger(this);
	
	private Header getHeader(HttpHeaders httpHeaders) {
		Header header = new Header();
		Map<String, String> headerMap = httpHeaders.toSingleValueMap();
		final ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		header.setOrgCode(headerMap.get("orgcode"));
		return header;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	//@Transactional(rollbackFor = Exception.class)
	public ResponseDto<Employee> saveEmployee(@RequestHeader(value = "accessToken") String accessToken,
			@RequestBody EmployeeDto employeeDto,@RequestHeader HttpHeaders headers, HttpServletResponse httpServletResponse) {
		Header header = getHeader(headers);
		logger.info("QuoationController-Inside save Method");
		ResponseDto<Employee> responseDto = new ResponseDto<Employee>();
		Employee employee=new Employee();
		UserDto userDto = jwtTokenManager.getUserDto(accessToken);
		if ((!Objects.isNull(userDto))) {
			employee=employeeService.saveEmployee(userDto, employeeDto);
			int httpStatusCode;
			httpStatusCode = HttpStatus.OK.value();
			httpServletResponse.setStatus(httpStatusCode);
			responseDto.setStatusCode(httpStatusCode);
			responseDto.setMessage(AppsConstant.RECORD_CREATED_SUCCESSFULLY);
			responseDto.setData(employee);
		} else {
			int httpStatusCode;
			httpStatusCode = HttpStatus.UNAUTHORIZED.value();
			httpServletResponse.setStatus(httpStatusCode);
			responseDto.setStatusCode(httpStatusCode);
			responseDto.setMessage(AppsConstant.UNAUTHORIZED_ACCESS_TOKEN);
		}
		return responseDto;
	}

	@GetMapping("/id")
	public ResponseDto<EmployeeDto> getById(@RequestHeader(value = "accessToken") String accessToken,
			@RequestParam(value = "empId") Long empId,@RequestHeader HttpHeaders headers, HttpServletResponse httpServletResponse) {
		logger.info("QuoteController-Inside getById Method");
		Header header = getHeader(headers);
		ResponseDto<EmployeeDto> responseDto = new ResponseDto<EmployeeDto>();
		int httpStatusCode;
		EmployeeDto employeeDto = employeeService.findById(empId);
		if (Objects.nonNull(employeeDto)) {
			httpStatusCode = HttpStatus.OK.value();
			httpServletResponse.setStatus(httpStatusCode);
			responseDto.setStatusCode(httpStatusCode);
			responseDto.setMessage(AppsConstant.RECORDS_FETCHED_SUCCESSFULLY);
			responseDto.setData(employeeDto);
		} else {
			httpStatusCode = HttpStatus.NOT_FOUND.value();
			httpServletResponse.setStatus(httpStatusCode);
			responseDto.setMessage(AppsConstant.UNAUTHORIZED_ACCESS_TOKEN);
		}

		return responseDto;
	}


	
	@GetMapping("/all")
	public ResponseDto<List<EmployeeDto>> getAllEmployee(
			@RequestHeader(value = "accessToken") String accessToken,@RequestHeader HttpHeaders headers, HttpServletResponse httpServletResponse) {
		logger.info("QuoteController-Inside getAllSalesQuote Method");
		Header header = getHeader(headers);
		ResponseDto<List<EmployeeDto>> responseDto = new ResponseDto<>();
		int httpStatusCode;
		UserDto userDto = jwtTokenManager.getUserDto(accessToken);
		if (Objects.nonNull(userDto)) {
			List<EmployeeDto> lstEmployee = employeeService.findAll();
			httpStatusCode = HttpStatus.OK.value();
			httpServletResponse.setStatus(httpStatusCode);
			responseDto.setStatusCode(httpStatusCode);
			responseDto.setMessage(AppsConstant.RECORDS_FETCHED_SUCCESSFULLY);
			responseDto.setData(lstEmployee);
		} else {
			httpStatusCode = HttpStatus.NOT_FOUND.value();
			httpServletResponse.setStatus(httpStatusCode);
			responseDto.setMessage(AppsConstant.UNAUTHORIZED_ACCESS_TOKEN);
		}

		return responseDto;
	}

	@PutMapping("/update")
	public ResponseDto<EmployeeDto> update(@RequestHeader(value = "accessToken") String accessToken,
			@RequestBody @Valid EmployeeDto employeeDto, HttpServletResponse httpServletResponse)
			throws UserException {

		logger.info("QuoteController-Inside add Method");
		ResponseDto<EmployeeDto> responseDto = new ResponseDto<EmployeeDto>();
		int httpStatusCode;

		UserDto userDto = jwtTokenManager.getUserDto(accessToken);
		if (Objects.nonNull(userDto)) {
			EmployeeDto emplDto = employeeService.update(employeeDto, userDto);
			if (Objects.nonNull(emplDto)) {
				httpStatusCode = HttpStatus.OK.value();
				httpServletResponse.setStatus(httpStatusCode);
				responseDto.setStatusCode(httpStatusCode);
				responseDto.setMessage(AppsConstant.RECORD_UPDATED_SUCCESSFULLY);
				responseDto.setData(emplDto);
			} else {
				httpStatusCode = HttpStatus.NOT_FOUND.value();
				httpServletResponse.setStatus(httpStatusCode);
				responseDto.setStatusCode(httpStatusCode);
				responseDto.setMessage(AppsConstant.ADDRESS_NOT_FOUND);
			}

		} else {
			httpStatusCode = HttpStatus.UNAUTHORIZED.value();
			responseDto.setMessage(AppsConstant.UNAUTHORIZED_ACCESS_TOKEN);
			responseDto.setStatusCode(httpStatusCode);
		}

		return responseDto;
	}
	

	
	
	@DeleteMapping("/delete")
	public ResponseDto<String> delete(@RequestHeader(value = "accessToken") String accessToken,
			@RequestParam(value = "id") Long id, HttpServletResponse httpServletResponse) {
		logger.info("QuoteController-Inside delete Method");

		ResponseDto<String> responseDto = new ResponseDto<String>();
		int httpStatusCode;

		UserDto userDto = jwtTokenManager.getUserDto(accessToken);
		if (Objects.nonNull(userDto)) {
			employeeService.delete(id);
			httpStatusCode = HttpStatus.OK.value();
			httpServletResponse.setStatus(httpStatusCode);
			responseDto.setMessage(AppsConstant.RECORD_DELETED_SUCCESSFULLY);
			responseDto.setStatusCode(httpStatusCode);
		} else {
			httpStatusCode = HttpStatus.UNAUTHORIZED.value();
			responseDto.setMessage(AppsConstant.UNAUTHORIZED_ACCESS_TOKEN);
			responseDto.setStatusCode(httpStatusCode);
		}
		return responseDto;
	}
	
	  
	        


}
