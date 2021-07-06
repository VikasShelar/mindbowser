package com.mind.bowser.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Role extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 6340959949579041105L;

	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String type;
	public Role(String type) {
		this.type = type;
	}

}
