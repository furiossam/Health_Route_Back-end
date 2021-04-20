package br.com.univali.healthroutes.api.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.univali.healthroutes.api.medicine.dao.MedicineDao;
import br.com.univali.healthroutes.api.medicine.model.Medicine;
import br.com.univali.healthroutes.api.patient.dao.PatientDao;
import br.com.univali.healthroutes.api.patient.model.Patient;
import br.com.univali.healthroutes.api.user.dao.UserDao;
import br.com.univali.healthroutes.api.user.model.User;

@Service
public class HealthRoutesServiceImpl implements HealthRoutesService {
	
	@Autowired
	private PatientDao patientDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private MedicineDao medicineDao;

	@Override
	public Page<Patient> list(Pageable pagination) throws SQLException {
		return patientDao.findAll(pagination);
	}

	@Override
	public User saveUser(User user) throws SQLException {
		return userDao.save(user);
	}

	@Override
	public void routeCalculation(int time, int vCapacity) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Patient savePatient(Patient patient) {
		return patientDao.save(patient);
	}

	@Override
	public Medicine saveMedicine(Medicine medicine) throws SQLException {
		return medicineDao.save(medicine);
	}

}
