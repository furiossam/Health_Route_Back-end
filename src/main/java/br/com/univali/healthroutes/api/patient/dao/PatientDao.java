package br.com.univali.healthroutes.api.patient.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.univali.healthroutes.api.patient.model.Patient;

@Repository
public interface PatientDao extends JpaRepository<Patient, Long>{

}
