package com.mind.bowser.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table()
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -5014637280128915021L;
	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String mobile;
	private String password;
	private LocalDate dob;
	@Pattern(regexp = "(^$|[0-9]{10})")
	@NotNull
	private String city;
	private String company;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "rule_id"))
	private Set<Role> roles = new HashSet<>();
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Address> address = new HashSet<>();

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	private JwtToken jwtToken;

	public User(String firstName, String email, String password) {
		super();
		this.firstName = firstName;
		this.email = email;
		this.password = password;

	}

	public User(String firstName, String email, String mobile, String password) {
		super();
		this.firstName = firstName;
		this.email = email;
		this.mobile = mobile;
		this.password = password;

	}

	public User(Long id, String firstName, String email, String mobile, String password) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.email = email;
		this.mobile = mobile;
		this.password = password;

	}

}
