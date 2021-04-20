package br.com.univali.healthroutes.api.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.univali.healthroutes.api.medicine.model.Medicine;
import br.com.univali.healthroutes.api.patient.model.Patient;
import br.com.univali.healthroutes.api.user.model.User;

@Service
public interface HealthRoutesService {
	 
	
	
	public Patient savePatient(Patient patient) throws SQLException;
	
	public Page<Patient> list(Pageable pagination) throws SQLException;
	
	public User saveUser(User user) throws SQLException;
	
	public Medicine saveMedicine(Medicine medicine) throws SQLException;

	public void routeCalculation(int time, int vCapacity) throws Exception;

	
}
