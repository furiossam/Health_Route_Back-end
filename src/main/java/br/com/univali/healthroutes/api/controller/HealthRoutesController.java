package br.com.univali.healthroutes.api.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.univali.healthroutes.api.AG.model.Individual;
import br.com.univali.healthroutes.api.AG.service.AGService;
import br.com.univali.healthroutes.api.service.HealthRoutesService;
import br.com.univali.healthroutes.api.user.dto.UserDto;
import br.com.univali.healthroutes.api.user.model.User;
import lombok.val;

@RestController
public class HealthRoutesController {

	private final static Logger LOGGER = LoggerFactory.getLogger(HealthRoutesController.class);

	@Autowired
	private HealthRoutesService service;

	@Autowired
	private AGService agService;

	@RequestMapping(value = "/teste", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String teste() {
		return String.valueOf(BCrypt.checkpw("9abc5aa4defb7330649236e16ad295eb",
				"$2a$12$AbU0XwlBPZQbkqMIy3yMwuxeDfcCBQPedSB.wUQnmZSf7TsT9P11a"));
	}

	@PostMapping(value = "/userSubmit")
	public ResponseEntity<UserDto> userSubmit(@RequestBody UserDto dto) {
		try {
			User user = UserDto.convertUser(dto);
			service.saveUser(user);
			return new ResponseEntity<>(null, HttpStatus.CREATED);
		} catch (SQLException ex) {
			LOGGER.warn("Erro na Criação de Usuário!");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/generateFirstGeneration")
	public ResponseEntity<Individual> generateFirstGeneration(@RequestParam int vCapacity, @RequestParam int timeWindow) {
		long start = System.currentTimeMillis();
		
		List<Individual> individuals = agService.generateFirstGenerationRandom(vCapacity, timeWindow, 100, 500);
			System.out.println("Score: " + individuals.get(0).getScore());
			System.out.println("Tempo: " + individuals.get(0).getTimeWindow());
			System.out.println("Distância Percorrida: " + individuals.get(0).getDistance());
			System.out.print("População:");
			for (int j = 0; j < individuals.get(0).getGenotype().size(); j++) {
				System.out.print(individuals.get(0).getGenotype().get(j).getName() + " ");
			}
			System.out.println();
		long elapsed = System.currentTimeMillis() - start;
		System.out.println("Tempo de Execução do cálculo da Rota: " + elapsed + " ms");
		System.out.println("Tamanho do genótipo: "+individuals.get(0).getGenotype().size());
		return new ResponseEntity<>(individuals.get(0), HttpStatus.ACCEPTED);
	}
	
	@GetMapping(value = "/calculus")
	public void calculus(@RequestParam int vCapacity, @RequestParam int timeWindow, @RequestParam double optimalDistance, @RequestParam int deliverys) {
		
		List<String> exceptions = new ArrayList<>();
		List<Individual> bestIndividuals = new ArrayList<>();
		double averageVariance = 0.0;
		long timer = 0;
		for (int i=0; i<100; i++)  {
			try {
				long start = System.currentTimeMillis();
				List<Individual> individuals = agService.generateFirstGenerationRandom(vCapacity, timeWindow, 100, 1000);
				if(individuals.get(0).getGenotype().size()==deliverys+2) {
					bestIndividuals.add(individuals.get(0));
					long elapsed = System.currentTimeMillis() - start;
					timer+=elapsed;
					
				}
				else {
					i--;
					exceptions.add("Inválido");
				}
				System.out.println("Geração " + i);
			} catch (Exception e) {
				i--;
				exceptions.add("Erro");
			}
		}
		
		for (Individual individual : bestIndividuals) {
			averageVariance += (optimalDistance-individual.getDistance())*100/optimalDistance;
			System.out.println("Variância do individuo "+averageVariance);
			
		}
		
		averageVariance = averageVariance/bestIndividuals.size();
		
		System.out.println("Tempo Médio de 100 Execuções " + (timer/100));
		System.out.println("MÉDIA DE VARIANCIAS: " + averageVariance);
		
	}
	
	@PatchMapping(value = "/deliverMedicines")
	public ResponseEntity<Void> deliverMedicines(@RequestBody List<Long> patientIds){
		agService.deliverMedicines(patientIds);
		return ResponseEntity.ok().build();
	}

}
