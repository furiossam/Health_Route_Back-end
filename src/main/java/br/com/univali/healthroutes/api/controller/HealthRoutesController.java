package br.com.univali.healthroutes.api.controller;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.univali.healthroutes.api.AG.model.Individual;
import br.com.univali.healthroutes.api.AG.service.AGService;
import br.com.univali.healthroutes.api.geo.service.GeoCodingService;
import br.com.univali.healthroutes.api.patient.model.Patient;
import br.com.univali.healthroutes.api.service.HealthRoutesService;
import br.com.univali.healthroutes.api.user.dto.UserDto;
import br.com.univali.healthroutes.api.user.model.User;

@RestController
public class HealthRoutesController {

	private final static Logger LOGGER = LoggerFactory.getLogger(HealthRoutesController.class);

	@Autowired
	private HealthRoutesService service;

	@Autowired
	private AGService agService;
	
	@Autowired
	private GeoCodingService geoService;

	@RequestMapping(value = "/teste", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String teste() {
		return String.valueOf(BCrypt.checkpw("9abc5aa4defb7330649236e16ad295eb",
				"$2a$12$AbU0XwlBPZQbkqMIy3yMwuxeDfcCBQPedSB.wUQnmZSf7TsT9P11a"));
	}

	@GetMapping(value = "/listPatients")
	public ResponseEntity<Page<Patient>> listPatients(Pageable pagination) {
		try {
			Page<Patient> patients = service.list(pagination);
			return new ResponseEntity<>(patients, HttpStatus.OK);
		} catch (SQLException e) {
			LOGGER.warn("Problema ao acessar a lista de pacientes!");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/userSubmit")
	public ResponseEntity<UserDto> cadastraUsuario(UserDto dto) {
		try {
			User user = UserDto.convertUser(dto);
			service.saveUser(user);
			return new ResponseEntity<>(dto, HttpStatus.CREATED);
		} catch (SQLException ex) {
			LOGGER.warn("Erro na Criação de Usuário!");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/patientSubmit")
	public ResponseEntity<Patient> submitPatient(Patient patient) {
		try {
			service.savePatient(patient);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (SQLException e) {
			LOGGER.warn("Erro na criação de paciente!");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/RouteCalculation")
	public ResponseEntity<?> routeCalculation(Integer duration, Integer vehicleCapacity) {
		try {
			service.routeCalculation(duration, vehicleCapacity);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.warn("Erro no cálculo de rota!");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/generateFirstGeneration")
	public ResponseEntity<?> generateFirstGeneration(@RequestParam int vCapacity, @RequestParam int timeWindow,
			@RequestParam int sizePop) {
		List<Individual> individuals = agService.generateFirstGenerationRandom(vCapacity, timeWindow, sizePop, 50);
		for (int i = 0; i < individuals.size(); i++) {
			System.out.println("Score: " + individuals.get(i).getScore());
			System.out.print("População:");
			for (int j = 0; j < individuals.get(i).getGenotype().size(); j++) {
				System.out.print(individuals.get(i).getGenotype().get(j).getName() + " ");
			}
			System.out.println();
		}
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

}
