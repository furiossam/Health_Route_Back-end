package br.com.univali.healthroutes.api.user.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.univali.healthroutes.api.user.model.User;

@Repository
public interface UserDao extends CrudRepository<User, Long>{
	
	User findByCpf(String cpf);
	
}
