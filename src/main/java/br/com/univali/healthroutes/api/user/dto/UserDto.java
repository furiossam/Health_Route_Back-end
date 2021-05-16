package br.com.univali.healthroutes.api.user.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.univali.healthroutes.api.login.model.Login;
import br.com.univali.healthroutes.api.user.model.User;

public class UserDto {
	
	private String cpf;
	private String nome;
	private String email;
	private String password;

	public String getCpf() {
		return cpf;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static User convertUser(UserDto dto) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		User user = new User(dto.getCpf(), dto.getNome(), dto.getEmail(), bCryptPasswordEncoder.encode(dto.getPassword()));
		return user;
	}

}