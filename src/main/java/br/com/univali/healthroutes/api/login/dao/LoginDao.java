package br.com.univali.healthroutes.api.login.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.univali.healthroutes.api.login.model.Login;

@Repository
public interface LoginDao extends CrudRepository<Login, Long>{

}
