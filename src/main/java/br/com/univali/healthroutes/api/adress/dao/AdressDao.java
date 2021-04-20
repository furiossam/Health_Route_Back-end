package br.com.univali.healthroutes.api.adress.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.univali.healthroutes.api.adress.model.Adress;

@Repository
public interface AdressDao extends JpaRepository<Adress, Long> {

}
