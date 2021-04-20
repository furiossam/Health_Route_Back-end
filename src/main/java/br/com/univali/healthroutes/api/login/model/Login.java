package br.com.univali.healthroutes.api.login.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "login")
public class Login {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_login;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "\"password\"")
	private String password;
	
	@Column(name = "\"enable\"")
	private Boolean enable;
	
	@Column(name = "createDate")
	private LocalDate createDate;
		
}
