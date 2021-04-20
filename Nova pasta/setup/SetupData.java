package br.com.univali.healthroutes.api.setup;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.univali.healthroutes.api.adress.model.Adress;
import br.com.univali.healthroutes.api.geo.model.Root;
import br.com.univali.healthroutes.api.geo.service.GeoCodingService;
import br.com.univali.healthroutes.api.medicine.dao.MedicineDao;
import br.com.univali.healthroutes.api.medicine.model.Medicine;
import br.com.univali.healthroutes.api.patient.dao.PatientDao;
import br.com.univali.healthroutes.api.patient.model.Patient;

@Component
public class SetupData implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	GeoCodingService geoService;
	
	@Autowired
	PatientDao dao;
	
	@Autowired
	MedicineDao medicineDao;
 
    boolean alreadySetup = false;
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
    	createPatients();
    	System.out.println("teste");
    	alreadySetup = true;
    	
    	
    	
    }
    
    
    public void createPatients() {
    	
    	
    	
    	
    	
    	
    	
    	
    	List<Patient> patients = new ArrayList<>();
    	Adress address = new Adress(1L, "Avenida das Tipuanas", 1271, "São Sebastião", "Palhoça", "SC", 0, 0, true, LocalDate.now() );
    	Root root = geoService.getBasicLatLog(address.getStreet()+ " , "+ address.getNumber()+
    			", "+ address.getDistrict()+", "+ address.getCounty()+", " + address.getState());
    	address.setLatitude(root.getLat());
    	address.setLongitude(root.getLong());
    	Medicine medicine = new Medicine(2L, "Novalgina", "Forte", false, LocalDate.now());
    	
    	medicine = medicineDao.save(medicine);
    	
    	List<Medicine> medicines = new ArrayList<>();
    	medicines.add(medicine);
    	patients.add(new Patient(3L, address, "Posto", "000000000", "00000000", LocalDate.now(), LocalDate.now().plusDays(15)));
    	
    	Adress address2 = new Adress(4L, "das Sibipirunas", 207, "São Sebastião", "Palhoça", "SC", 0, 0, LocalDate.now() );
    	Root root2 = geoService.getBasicLatLog("Rua "+ address2.getStreet()+ " , "+ address2.getNumber()+
    			", "+ address2.getDistrict()+", "+ address2.getCounty()+", " + address2.getState());
    	address2.setLatitude(root2.getLat());
    	address2.setLongitude(root2.getLong());
    	patients.add(new Patient(5L, address2, "Alfredo", "000000000", "00000000", LocalDate.now(), LocalDate.now().plusDays(15)));
    	
    	Adress address3 = new Adress(6L, "dos Palmiteiros", 110, "São Sebastião", "Palhoça", "SC", 0, 0, LocalDate.now() );
    	Root root3 = geoService.getBasicLatLog("Rua "+ address3.getStreet()+ " , "+ address3.getNumber()+
    			", "+ address3.getDistrict()+", "+ address3.getCounty()+", " + address3.getState());
    	address3.setLatitude(root3.getLat());
    	address3.setLongitude(root3.getLong());
    	patients.add(new Patient(7L, address3, "Gilberto", "000000000", "00000000", LocalDate.now(), LocalDate.now().plusDays(15)));
    	
    	Adress address4 = new Adress(8L, "Tomaz Domingos da Silveira", 3751, "São Sebastião", "Palhoça", "SC", 0, 0, LocalDate.now() );
    	Root root4 = geoService.getBasicLatLog("Rua "+ address4.getStreet()+ " , "+ address4.getNumber()+
    			", "+ address4.getDistrict()+", "+ address4.getCounty()+", " + address4.getState());
    	address4.setLatitude(root4.getLat());
    	address4.setLongitude(root4.getLong());
    	patients.add(new Patient(9L, address4, "Milene", "000000000", "00000000", LocalDate.now(), LocalDate.now().plusDays(15)));
    	
    	Adress address5 = new Adress(10L, "dos Palmiteiros", 180, "São Sebastião", "Palhoça", "SC", 0, 0, LocalDate.now() );
    	Root root5 = geoService.getBasicLatLog("Rua "+ address5.getStreet()+ " , "+ address5.getNumber()+
    			", "+ address5.getDistrict()+", "+ address5.getCounty()+", " + address5.getState());
    	address5.setLatitude(root5.getLat());
    	address5.setLongitude(root5.getLong());
    	patients.add(new Patient(11L, address5, "Valter", "000000000", "00000000", LocalDate.now(), LocalDate.now().plusDays(15)));
    	
    	patients = dao.saveAll(patients);
    	
    	patients.get(0).setMedicines(medicines);
    	patients.get(1).setMedicines(medicines);
    	patients.get(2).setMedicines(medicines);
    	patients.get(3).setMedicines(medicines);
    	patients.get(4).setMedicines(medicines);
    	
    	dao.saveAll(patients);
    	
    	System.out.println(patients.get(0).getName());
    }
}

