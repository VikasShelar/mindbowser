package com.mind.bowser.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class JwtToken implements Serializable {

	private static final long serialVersionUID = 5381688550247590782L;

	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 3000)
	private String accessToken;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;

	public JwtToken(String accessToken, User user) {
		super();
		this.accessToken = accessToken;
		this.user = user;
	}

}
