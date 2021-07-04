package com.mind.bowser.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MANAGER")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Manager {
	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Email(message = "Email should be valid")
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private String address;
    private LocalDate date;
	private String company;
	
	

}
