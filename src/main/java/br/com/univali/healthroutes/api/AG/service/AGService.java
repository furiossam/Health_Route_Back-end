package br.com.univali.healthroutes.api.AG.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.univali.healthroutes.api.AG.model.Individual;
import br.com.univali.healthroutes.api.patient.model.Patient;

@Service
public interface AGService {

	public List<Individual> generateFirstGenerationRandom(int vehycleCapacity, double timeWindow, int sizePopulation, int generations);
	
	public double individualTimeWindowCalculator(List<Integer> indexes);
	
	public int individualCapacityCalculator(List<Patient> patientsAux);
	
	public void setMatrices(List<Patient> patients);
	
	public double calculateScore(double timeWindowIndividualCounter, double vCapacityIndividualCounter, int size, double multiplicator, List<Patient> patients);
	
	public double calculateCriticalityMultiplicator(List<Patient> patients);
}
