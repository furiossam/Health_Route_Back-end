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
    	
    	Adress address2 = new Adress(4L, "Unide", 242, "Pagani", "Palhoça", "SC", 0, 0, LocalDate.now() );
    	Root root2 = geoService.getBasicLatLog("Rua "+ address2.getStreet()+ " , "+ address2.getNumber()+
    			", "+ address2.getDistrict()+", "+ address2.getCounty()+", " + address2.getState());
    	address2.setLatitude(root2.getLat());
    	address2.setLongitude(root2.getLong());
    	patients.add(new Patient(5L, address2, "Alfredo", "000000000", "00000000", LocalDate.now(), LocalDate.now().plusDays(15)));
    	
    	Adress address3 = new Adress(6L, "São Francisco do Sul", 510, "Jardim Aquarius", "Palhoça", "SC", 0, 0, LocalDate.now() );
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
    	
    	Adress address6 = new Adress(12L, "Pref. Reinoldo Alves", 1186, "Passa Vinte", "Palhoça", "SC", 0, 0, LocalDate.now() );
    	Root root6 = geoService.getBasicLatLog("Rua "+ address6.getStreet()+ " , "+ address6.getNumber()+
    			", "+ address6.getDistrict()+", "+ address6.getCounty()+", " + address6.getState());
    	address6.setLatitude(root6.getLat());
    	address6.setLongitude(root6.getLong());
    	patients.add(new Patient(13L, address6, "Ronaldo", "000000000", "00000000", LocalDate.now(), LocalDate.now().plusDays(15)));
    	
    	Adress address7 = new Adress(14L, "Tomaz Domingos da Silveira", 2315, "São Sebastião", "Palhoça", "SC", 0, 0, LocalDate.now() );
    	Root root7 = geoService.getBasicLatLog("Rua "+ address7.getStreet()+ " , "+ address7.getNumber()+
    			", "+ address7.getDistrict()+", "+ address7.getCounty()+", " + address7.getState());
    	address7.setLatitude(root7.getLat());
    	address7.setLongitude(root7.getLong());
    	patients.add(new Patient(15L, address7, "Virgulino", "000000000", "00000000", LocalDate.now(), LocalDate.now().plusDays(15)));
    	
    	Adress address8 = new Adress(16L, "Tomaz Domingos da Silveira", 803, "São Sebastião", "Palhoça", "SC", 0, 0, LocalDate.now() );
    	Root root8 = geoService.getBasicLatLog("Rua "+ address8.getStreet()+ " , "+ address8.getNumber()+
    			", "+ address8.getDistrict()+", "+ address8.getCounty()+", " + address8.getState());
    	address8.setLatitude(root8.getLat());
    	address8.setLongitude(root8.getLong());
    	patients.add(new Patient(17L, address8, "Terezinha", "000000000", "00000000", LocalDate.now(), LocalDate.now().plusDays(15)));
    	
    	Adress address9 = new Adress(18L, "Jaime Silveira de Souza", 125, "São Sebastião", "Palhoça", "SC", 0, 0, LocalDate.now() );
    	Root root9 = geoService.getBasicLatLog("Rua "+ address9.getStreet()+ " , "+ address9.getNumber()+
    			", "+ address9.getDistrict()+", "+ address9.getCounty()+", " + address9.getState());
    	address9.setLatitude(root9.getLat());
    	address9.setLongitude(root9.getLong());
    	patients.add(new Patient(19L, address9, "Antonina", "000000000", "00000000", LocalDate.now(), LocalDate.now().plusDays(15)));
    	
    	Adress address10 = new Adress(20L, "Ver. Osvaldo de Oliveira", 3819, "Centro", "Palhoça", "SC", 0, 0, LocalDate.now() );
    	Root root10 = geoService.getBasicLatLog("Rua "+ address10.getStreet()+ " , "+ address10.getNumber()+
    			", "+ address10.getDistrict()+", "+ address10.getCounty()+", " + address10.getState());
    	address10.setLatitude(root10.getLat());
    	address10.setLongitude(root10.getLong());
    	patients.add(new Patient(21L, address10, "Jussara", "000000000", "00000000", LocalDate.now(), LocalDate.now().plusDays(15)));
    	
    	Adress address11 = new Adress(22L, "Zélia Maria dos Santos", 70, "São Sebastião", "Palhoça", "SC", 0, 0, LocalDate.now() );
    	Root root11 = geoService.getBasicLatLog("Rua "+ address11.getStreet()+ " , "+ address11.getNumber()+
    			", "+ address11.getDistrict()+", "+ address11.getCounty()+", " + address11.getState());
    	address11.setLatitude(root11.getLat());
    	address11.setLongitude(root11.getLong());
    	patients.add(new Patient(23L, address11, "Leonardo", "000000000", "00000000", LocalDate.now(), LocalDate.now().plusDays(15)));
    	
    	Adress address12 = new Adress(24L, "Pero Vaz de Caminha", 917, "Caminho Novo", "Palhoça", "SC", 0, 0, LocalDate.now() );
    	Root root12 = geoService.getBasicLatLog("Rua "+ address12.getStreet()+ " , "+ address12.getNumber()+
    			", "+ address12.getDistrict()+", "+ address12.getCounty()+", " + address12.getState());
    	address12.setLatitude(root12.getLat());
    	address12.setLongitude(root12.getLong());
    	patients.add(new Patient(25L, address12, "Gioconda", "000000000", "00000000", LocalDate.now(), LocalDate.now().plusDays(15)));
    	
    	patients = dao.saveAll(patients);
    	
    	for (Patient patient : patients) {
    		patient.setMedicines(medicines);
		}
    	
    	dao.saveAll(patients);
    	
    	System.out.println(patients.get(0).getName());
    }
}

