package com.mind.bowser.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EMPLOYEE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
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
