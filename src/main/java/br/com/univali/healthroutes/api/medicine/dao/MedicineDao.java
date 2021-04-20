package br.com.univali.healthroutes.api.medicine.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.univali.healthroutes.api.medicine.model.Medicine;

@Repository
public interface MedicineDao  extends JpaRepository<Medicine, Long>{

}
