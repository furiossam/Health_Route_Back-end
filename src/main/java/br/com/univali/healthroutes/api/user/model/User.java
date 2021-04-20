package br.com.univali.healthroutes.api.user.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import br.com.univali.healthroutes.api.login.model.Login;
import lombok.Data;

@Entity
@Data
@Table(name = "\"user\"")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_user;

	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "\"name\"")
	private String name;
	
	@Column(name = "email")
	private String email;
	
	@OneToOne
	@JoinColumn(name="id_login")	
	@NotNull
	private Login login;
	
	@Column(name = "isActive")
	private Boolean isActive=false;
	
	@Column(name = "createDate")
	private LocalDate createDate;
	
	public User(Long id, String cpf, String name, String email, Login login, Boolean isActive) {
		this.id_user = id;
		this.cpf = cpf;
		this.name = name;
		this.email = email;
		this.login = login;
		this.isActive = isActive;
	}

}