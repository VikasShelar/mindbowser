package com.mind.bowser.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Address extends BaseEntity implements Serializable {

	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String local1;
	private String local2;
	private String area;
	private Long pincode;
	private String longitude;
	private String latitude;
	private String title;
	private Boolean isDefault = false;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Address(Long id, String local1, String local2, String area, Long pincode, String longitude, String latitude,
			String title, Boolean isDefault) {
		super();
		this.id = id;
		this.local1 = local1;
		this.local2 = local2;
		this.area = area;
		this.pincode = pincode;
		this.longitude = longitude;
		this.latitude = latitude;
		this.title = title;
		this.isDefault = isDefault;
	}

	public Address(User user) {
		this.user = user;
	}

}
