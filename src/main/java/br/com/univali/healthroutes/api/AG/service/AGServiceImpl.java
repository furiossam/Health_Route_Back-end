package br.com.univali.healthroutes.api.AG.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.univali.healthroutes.api.AG.model.Individual;
import br.com.univali.healthroutes.api.adress.model.Adress;
import br.com.univali.healthroutes.api.distanceMatrix.model.Result;
import br.com.univali.healthroutes.api.distanceMatrix.model.Root;
import br.com.univali.healthroutes.api.distanceMatrix.service.DistanceMatrixService;
import br.com.univali.healthroutes.api.patient.dao.PatientDao;
import br.com.univali.healthroutes.api.patient.model.Patient;

@Service
public class AGServiceImpl implements AGService {

	@Autowired
	PatientDao patientDao;

	@Autowired
	DistanceMatrixService serviceMatrix;

	private List<Patient> patients;

	private int indexDepot;

	public final double AVERAGE_SPEED = 35000.0;

	double[][] matrixTravelDistance;
	double[][] matrixTimeTravel;

	public int returnIndexDepot(List<Patient> patients) {
		for (int i = 0; i < patients.size(); i++) {
			if (patients.get(i).getAdress().isDepot()) {
				indexDepot = i;
			}
		}
		return indexDepot;
	}

	public int returnIndexRandom(int indexDepot, List<Integer> indexes) {
		Random random = new Random(patients.size() - 1);
		int indexRandom = -1;
		do {
			indexRandom = random.nextInt(patients.size());
		} while ((indexRandom == indexDepot) || (indexes.contains(indexRandom)));
		return indexRandom;
	}

	@Override
	public List<Individual> generateFirstGenerationRandom(int vehycleCapacity, double timeWindow, int sizePopulation,
			int generations) {
		/*
		 * Geração dos individuos randomicamente seguindo critérios da capacidade do
		 * veiculo e do tempo disponivel para a rota.
		 */
		List<Individual> individuals = new ArrayList<>();
		patients = patientDao.findAll();
		setMatrices(patients);
		indexDepot = returnIndexDepot(patients);
		for (int i = 0; i < sizePopulation*10; i++) {
			List<Patient> individualRoute = new ArrayList<>();
			List<Adress> adresses = new ArrayList<>();
			List<Integer> indexes = new ArrayList<>();
			individualRoute.add(patients.get(indexDepot));
			adresses.add(patients.get(indexDepot).getAdress());
			indexes.add(indexDepot);
			Random generator = new Random();
			int indexRandom = 0;
			double timeWindownCounter = 0;
			int capacityCounter = 0;
			do {
				do {
					indexRandom = generator.nextInt(patients.size());
				} while (indexes.contains(indexRandom));
				if (!patients.get(indexRandom).getAdress().isDepot()) {
					individualRoute.add(patients.get(indexRandom));
					adresses.add(patients.get(indexRandom).getAdress());
					indexes.add(indexRandom);
					capacityCounter = individualCapacityCalculator(individualRoute);
					timeWindownCounter = individualTimeWindowCalculator(indexes);
				}
			} while ((timeWindownCounter <= timeWindow ) && (capacityCounter <= vehycleCapacity));
			individualRoute.remove(individualRoute.size() - 1);
			indexes.remove(indexes.size() - 1);
			individualRoute.add(patients.get(indexDepot));
			indexes.add(indexDepot);
			double multiplicator = calculateCriticalityMultiplicator(individualRoute);
			double timeWindowIndividual = individualTimeWindowCalculator(indexes);
			double distanceIndividual = individualDistanceCalculator(indexes);
			double score = calculateScore(timeWindowIndividual, capacityCounter, individualRoute.size(), multiplicator,
					individualRoute, distanceIndividual);
			Individual individual = new Individual(individualRoute, score, indexes, timeWindowIndividual, distanceIndividual);
			individuals.add(individual);
		}
		individuals.sort(Comparator.comparingDouble(Individual::getScore).reversed());
		int genotypeSizeMax = individuals.get(0).getGenotype().size()-1;
		List<Individual> individualsAux = new ArrayList<>();
		for (int i = 0; i < individuals.size(); i++) {
			if(individuals.get(i).getIndexes().size()==genotypeSizeMax) {
				individualsAux.add(individuals.get(i));
			}
		}
		int adder = 0;
		do {
			individualsAux.add(individualsAux.get(adder));
			adder++;
		}while(individualsAux.size()<100);


		for (int i = 0; i < generations; i++) {
			individualsAux = crossoverElitistTwoPoints(individualsAux, sizePopulation, timeWindow, vehycleCapacity);
		}
		individualsAux.sort(Comparator.comparingDouble(Individual::getScore).reversed());
		
		do {
			individualsAux.remove(individualsAux.size()-1);
		}while(individualsAux.size()>=100);
		return individualsAux;
	}

	public List<Individual> crossoverElitistTwoPoints(List<Individual> individuals, int sizePopulation, double timeWindow, int vehycleCapacity) {

		Random generator = new Random();
		for (int i = 0; i < 80; i+=2) {
			int rand1 = generator.nextInt(individuals.size());
			int rand2 = generator.nextInt(individuals.size());
			
			
			
			
			List<Individual> filhos = oxOperator(individuals.get(rand1), individuals.get(rand2), timeWindow, vehycleCapacity);
			
			for (Individual individual : filhos) {
				individuals.add(individual);
			}	
				
		}
		
		individuals.sort(Comparator.comparingDouble(Individual::getScore).reversed());
		List<Individual> individualsFinal = new ArrayList<>();
		for (int i1 = 0; i1 < sizePopulation; i1++) {
			individualsFinal.add(individuals.get(i1));
		}
		individuals.clear();
		return individualsFinal;
	}
	
	public List<Individual> oxOperator(Individual individual1, Individual individual2,  double timeWindow, int vehycleCapacity) {
		Patient[] child1 = new Patient[individual1.getGenotype().size()];
		Integer[] indexes1 = new Integer[individual1.getGenotype().size()];
		
		Patient[] child2 = new Patient[individual2.getGenotype().size()];
		Integer[] indexes2 = new Integer[individual2.getGenotype().size()];
		
		
		Random generator = new Random();
		int rand1 = generator.nextInt(individual1.getGenotype().size()-2)+1;
		int rand2 = generator.nextInt(individual1.getGenotype().size()-2)+1;
		int inferioLimit = 0;
		int superiotLimit = 0;
		
		if(rand1<rand2) {
			inferioLimit = rand1;
			superiotLimit = rand2;
		}else {
			inferioLimit = rand2;
			superiotLimit = rand1;
		}
		
		//int inferioLimit = individual1.getGenotype().size()/3;
		//int superiotLimit = individual1.getGenotype().size()/3*2;
		
		for (int i = inferioLimit; i <= superiotLimit; i++) {
			child1[i] = individual1.getGenotype().get(i);
			indexes1[i] = individual1.getIndexes().get(i);
			
			child2[i] = individual2.getGenotype().get(i);
			indexes2[i] = individual2.getIndexes().get(i);
		}
		int cont = 1;
		
			for (int i = 1; i < individual2.getGenotype().size()-1; i++) {
				if(!(Arrays.asList(indexes1).contains(individual2.getIndexes().get(i)))) {
					if(cont==inferioLimit) {
						cont=superiotLimit+1;
					}
					if(cont==individual2.getGenotype().size()-1) {
						break;
					}			
					if(indexes1[cont]==null) {
						child1[cont] = individual2.getGenotype().get(i);
						indexes1[cont] = individual2.getIndexes().get(i);
						cont++;
					}
				}
			}
		
		child1[individual2.getGenotype().size()-1]= individual2.getGenotype().get(individual2.getGenotype().size()-1);
		child1[0]= individual2.getGenotype().get(0);
		indexes1[0]=individual2.getIndexes().get(0);
		indexes1[individual2.getIndexes().size()-1]=individual2.getIndexes().get(individual2.getIndexes().size()-1);
		
		cont = 1;
		
		for (int j = 1; j < individual1.getGenotype().size()-1; j++) {
			if(!(Arrays.asList(indexes2).contains(individual1.getIndexes().get(j)))) {
				if(cont==inferioLimit) {
					cont=superiotLimit+1;
				}
				if(cont==individual1.getGenotype().size()-1) {
					break;
				}			
				if(indexes2[cont]==null) {
					child2[cont] = individual1.getGenotype().get(j);
					indexes2[cont] = individual1.getIndexes().get(j);
					cont++;
				}
			}
		}
		
		
		List<Individual> filhos = new ArrayList<Individual>();
		
		child2[individual1.getGenotype().size()-1]= individual1.getGenotype().get(individual1.getGenotype().size()-1);
		child2[0]= individual1.getGenotype().get(0);
		indexes2[0]=individual1.getIndexes().get(0);
		indexes2[individual1.getIndexes().size()-1]=individual1.getIndexes().get(individual1.getIndexes().size()-1);
		
		if(!(Arrays.asList(indexes1).contains(null))){
			double child1TimeWindow = individualTimeWindowCalculator(Arrays.asList(indexes1));
			int child1Capacity = individualCapacityCalculator(Arrays.asList(child1));
			double child1Multiplicator = calculateCriticalityMultiplicator(Arrays.asList(child1));
			double child1Distance = individualDistanceCalculator(Arrays.asList(indexes1));
			double child1Score = calculateScore(child1TimeWindow, child1Capacity, child1.length, child1Multiplicator, Arrays.asList(child1), child1Distance);
			
			Individual individual3 = new Individual(Arrays.asList(child1), child1Score, Arrays.asList(indexes1), child1TimeWindow, child1Distance);
			if(child1TimeWindow<=timeWindow && child1Capacity <= vehycleCapacity) {
				filhos.add(individual3);
			}
			
		}
		
		if(!(Arrays.asList(indexes2).contains(null))) {
			double child2TimeWindow = individualTimeWindowCalculator(Arrays.asList(indexes2));
			int child2Capacity = individualCapacityCalculator(Arrays.asList(child2));
			double child2Multiplicator = calculateCriticalityMultiplicator(Arrays.asList(child2));
			double child2Distance = individualDistanceCalculator(Arrays.asList(indexes2));
			double child2Score = calculateScore(child2TimeWindow, child2Capacity, child2.length, child2Multiplicator, Arrays.asList(child2), child2Distance);
			
			Individual individual4 = new Individual(Arrays.asList(child2), child2Score, Arrays.asList(indexes2), child2TimeWindow, child2Distance);
			if(child2TimeWindow<=timeWindow && child2Capacity <= vehycleCapacity) {
				filhos.add(individual4);
			}
		}
		
		// Mutação 1 porcento de chance
		
				Random mutation = new Random();
				if (mutation.nextInt(100) < 1 && filhos.size()>0) {
					int indexMutated = mutation.nextInt(filhos.size());
					
					List<Patient> patientAux = filhos.get(indexMutated).getGenotype();
					List<Integer> indexes = filhos.get(indexMutated).getIndexes();
					//for (int i = 0; i < 2; i++) {
						int firstIndex = mutation.nextInt(patientAux.size() - 2) + 1;
						int secondtIndex = mutation.nextInt(patientAux.size() - 2) + 1;
						Collections.swap(patientAux, firstIndex, secondtIndex);
						Collections.swap(indexes, firstIndex, secondtIndex);
					//}
					
					
					int capacityCounter = individualCapacityCalculator(patientAux);

					double multiplicator = calculateCriticalityMultiplicator(patientAux);
					
					double timeWindowIndividual = individualTimeWindowCalculator(indexes);
					double distanceIndividual = individualDistanceCalculator(indexes);
					double score = calculateScore(timeWindowIndividual, capacityCounter, patientAux.size(), multiplicator,
							patientAux, distanceIndividual);
					Individual individual = new Individual(patientAux, score, indexes, timeWindowIndividual, distanceIndividual);
					if(distanceIndividual<=timeWindow) {
						filhos.remove(indexMutated);
						filhos.add(individual);
						
					}
					
				}
		
		return filhos;
	}

	public double individualTimeWindowCalculator(List<Integer> indexes) {
		double timeWindowIndividual = 0;
		for (int i = 0; i < indexes.size() - 1; i++) {
			timeWindowIndividual += matrixTimeTravel[indexes.get(i)][indexes.get(i + 1)];
		}
		return timeWindowIndividual;
	}
	
	public double individualDistanceCalculator(List<Integer> indexes) {
		double travelDistanceIndividual = 0;
		for (int i = 0; i < indexes.size() - 1; i++) {
			travelDistanceIndividual += matrixTravelDistance[indexes.get(i)][indexes.get(i + 1)];
		}
		return travelDistanceIndividual;
	}

	public int individualCapacityCalculator(List<Patient> patientsAux) {
		int individualCapacityValue = 0;
		for (int i = 1; i < patientsAux.size(); i++) {
			for (int j = 0; j < patientsAux.get(i).getMedicines().size(); j++) {
				if (patientsAux.get(i).getMedicines().get(j).getThermolabile())
					individualCapacityValue += 2;
				else
					individualCapacityValue++;
			}
		}
		return individualCapacityValue;
	}

	@Override
	public double calculateCriticalityMultiplicator(List<Patient> patients) {
		double multiplicator = 1;
		for (int i = 1; i < patients.size() - 2; i++) {
			if (calculateDaysBetween(patients.get(i).getDeadLine()) < 0) {
				multiplicator += 10;
			} else if (calculateDaysBetween(patients.get(i).getDeadLine()) < 1) {
				multiplicator += 5;
			} else if (calculateDaysBetween(patients.get(i).getDeadLine()) < 2) {
				multiplicator += 4;
			} else if (calculateDaysBetween(patients.get(i).getDeadLine()) < 3) {
				multiplicator += 3.5;
			} else if (calculateDaysBetween(patients.get(i).getDeadLine()) < 4) {
				multiplicator += 3;
			} else if (calculateDaysBetween(patients.get(i).getDeadLine()) < 5) {
				multiplicator += 2.5;
			} else if (calculateDaysBetween(patients.get(i).getDeadLine()) < 10) {
				multiplicator += 2;
			} else {
				multiplicator += 1;
			}
		}
		return multiplicator;
	}

	public double calculateScore(double timeWindowIndividualCounter, double vCapacityIndividualCounter, int size,
			double multiplicator, List<Patient> patients, double distanceIndividual) {

		double repeatCounter = 0;
		for (int i = 1; i < patients.size() - 1; i++) {
			if (patients.get(i).getAdress().isDepot())
				repeatCounter += 10000.0;
			for (int j = 1; j < patients.size() - 1; j++) {
				if (i != j) {
					if (patients.get(i).getName() == patients.get(j).getName()) {
						repeatCounter += 10000.0;
					}
				}
			}
		}

		return ((((((size * 1000) / (timeWindowIndividualCounter * 10)) + (vCapacityIndividualCounter * 10))
				* multiplicator)) - repeatCounter);
	}

	public void setMatrices(List<Patient> patients) {
		patients.add(patients.size(), patients.get(0));
		List<Adress> adresses = new ArrayList<>();
		matrixTravelDistance = new double[patients.size()][patients.size()];
		matrixTimeTravel = new double[patients.size()][patients.size()];
		for (int i = 0; i < patients.size(); i++) {
			adresses.add(patients.get(i).getAdress());
		}
		Root root = serviceMatrix.getMatrix(adresses);
		for (Result result : root.getResourceSets().get(0).getResources().get(0).getResults()) {
			matrixTravelDistance[result.getOriginIndex()][result.getDestinationIndex()] = result.getTravelDistance()
					* 1000;
			matrixTimeTravel[result.getOriginIndex()][result
					.getDestinationIndex()] = (((result.getTravelDistance() * 1000) / AVERAGE_SPEED) * 60) + 5;
		}
	}

	private int calculateDaysBetween(LocalDate deadline) {

		return (int) ChronoUnit.DAYS.between(LocalDate.now(), deadline);

	}

	@Override
	public void deliverMedicines(List<Long> patientIds) {
		List<Patient> patients = patientDao.findAllById(patientIds);
		for (Patient patient : patients) {
			patient.setDeadLine(patient.getDeadLine().plusDays(30));
		}
		patientDao.saveAll(patients);
		
	}

}
