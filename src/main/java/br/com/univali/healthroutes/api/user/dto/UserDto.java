package br.com.univali.healthroutes.api.user.dto;

import br.com.univali.healthroutes.api.login.model.Login;
import br.com.univali.healthroutes.api.user.model.User;

public class UserDto {
	
	private Long id;
	private String cpf;
	private String nome;
	private String email;
	private Login login;
	private Boolean isActive=false;
	
	public Long getId() {
		return id;
	}

	public String getCpf() {
		return cpf;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public Login getLogin() {
		return login;
	}

	public Boolean getIsActive() {
		return isActive;
	}
	
	public static User convertUser(UserDto dto) {
		User user = new User(dto.getId(), dto.getCpf(), dto.getNome(), dto.getEmail(), dto.getLogin(), dto.getIsActive());
		return user;
	}

}