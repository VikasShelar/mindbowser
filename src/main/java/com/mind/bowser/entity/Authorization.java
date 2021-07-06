package com.mind.bowser.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Authorization extends BaseEntity {

	private static final long serialVersionUID = 2781991474253755338L;
	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	@NotNull
	private String url;
	private String role;
	@Transient
	private Set<Role> roles;

	public Authorization(Long id, @NotNull String url, String role) {
		this.id = id;
		this.url = url;
		this.role = role;
	}

}
