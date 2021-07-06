package com.mind.bowser.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1785107097193240383L;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	@PrePersist
	public void PrePersist() {
		this.createDate = new Date();
		this.updateDate = new Date();
	}

	@PreUpdate
	public void preUpdate() {
		this.updateDate = new Date();
	}

}
