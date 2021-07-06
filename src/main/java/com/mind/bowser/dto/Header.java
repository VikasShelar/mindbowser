package com.mind.bowser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Header {
	
	@JsonProperty
	private String orgCode;
	@JsonProperty
	private String todaysDate;

}
