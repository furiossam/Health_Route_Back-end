package br.com.univali.healthroutes.api.AG.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.univali.healthroutes.api.AG.model.AG;
import br.com.univali.healthroutes.api.AG.model.Individual;
import br.com.univali.healthroutes.api.AG.model.Selection;
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

	double[][] matrixTravelDistance;
	double[][] matrixTimeTravel;

	public AG buildAG(double mutation, Enum selection) {
		AG ag = new AG();
		if (selection == Selection.RANK) {

		}
		if (selection == Selection.ROULETTE) {

		}
		if (selection == Selection.TOURNAMENT) {

		}
		return ag;
	}

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
	public List<Individual> generateFirstGenerationRandom(int vehycleCapacity, double timeWindow, int sizePopulation, int generations) {
		/*
		 * Geração dos individuos randomicamente seguindo critérios da capacidade do
		 * veiculo e do tempo disponivel para a rota.
		 */
		List<Individual> individuals = new ArrayList<>();
		patients = patientDao.findAll();
		setMatrices(patients);
		indexDepot = returnIndexDepot(patients);
		showMatrices(matrixTimeTravel, matrixTravelDistance);
		for (int i = 0; i < sizePopulation; i++) {
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
			} while ((timeWindownCounter <= timeWindow) && (capacityCounter <= vehycleCapacity));
			individualRoute.remove(individualRoute.size() - 1);
			indexes.remove(indexes.size() - 1);
			individualRoute.add(patients.get(indexDepot));
			indexes.add(indexDepot);
			double multiplicator = calculateCriticalityMultiplicator(individualRoute);
			double score = calculateScore(timeWindownCounter, capacityCounter, individualRoute.size(), multiplicator, individualRoute);
			Individual individual = new Individual(individualRoute, score, indexes);
			individuals.add(individual);
		}
		for (int i = 0; i < generations; i++) {
			individuals = crossoverElitistTwoPoints(individuals,sizePopulation);
		}
		return individuals;
	}

	public List<Individual> crossoverElitistTwoPoints(List<Individual> individuals, int sizePopulation) {
		int inferioLimit = 0;
		int superiotLimit = 0;
		Random generator = new Random();
		for (int i = 0; i < individuals.size() / 2; i++) {
			int indexfirstParent = generator.nextInt(individuals.size());
			int indexSecondParent = generator.nextInt(individuals.size());
			inferioLimit = generator.nextInt(individuals.get(indexfirstParent).getGenotype().size() / 2);
			superiotLimit = generator.nextInt(individuals.get(indexfirstParent).getGenotype().size() / 2)
					+ individuals.get(indexfirstParent).getGenotype().size() / 2;
			List<Patient> patientAux = new ArrayList<>();
			List<Integer> indexes = new ArrayList<>();
			for(int j= 0; j<inferioLimit;j++) {
				patientAux.add(individuals.get(indexfirstParent).getGenotype().get(j));
				indexes.add(individuals.get(indexfirstParent).getIndexes().get(j));
			}
			for(int j = inferioLimit; j<superiotLimit;j++) {
				patientAux.add(individuals.get(indexSecondParent).getGenotype().get(j));
				indexes.add(individuals.get(indexSecondParent).getIndexes().get(j));
			}
			for(int j = superiotLimit;j<individuals.get(indexfirstParent).getGenotype().size();j++) {
				patientAux.add(individuals.get(indexfirstParent).getGenotype().get(j));
				indexes.add(individuals.get(indexfirstParent).getIndexes().get(j));
			}
			double timeWindowCounter = individualTimeWindowCalculator(indexes);
			int capacityCounter = individualCapacityCalculator(patientAux);
			double multiplicator = calculateCriticalityMultiplicator(patientAux);
			double score = calculateScore(timeWindowCounter, capacityCounter, patientAux.size(), multiplicator, patientAux);
			Individual individual = new Individual(patientAux, score, indexes);
			individuals.add(individual);
		}
		individuals.sort(Comparator.comparingDouble(Individual::getScore).reversed());
		List<Individual> individualsFinal = new ArrayList<>();
		for (int i = 0; i < sizePopulation; i++) {
			individualsFinal.add(individuals.get(i));
		}
		return individualsFinal;
	}

	private void showMatrices(double[][] matrixTimeTravel2, double[][] matrixTravelDistance2) {
		System.out.println("Matriz Tempo");
		for (int i = 0; i < matrixTimeTravel2.length; i++) {
			for (int j = 0; j < matrixTimeTravel2.length; j++) {
				System.out.print(matrixTimeTravel2[i][j] + " ");
			}
			System.out.println();
		}

		System.out.println("Matriz Distância");
		for (int i = 0; i < matrixTravelDistance2.length; i++) {
			for (int j = 0; j < matrixTravelDistance2.length; j++) {
				System.out.print(matrixTravelDistance2[i][j] + " ");
			}
			System.out.println();
		}

	}

	public double individualTimeWindowCalculator(List<Integer> indexes) {
		double timeWindowIndividual = 0;
		for (int i = 0; i < indexes.size() - 1; i++) {
			timeWindowIndividual += matrixTimeTravel[indexes.get(i)][indexes.get(i + 1)];
		}
		return timeWindowIndividual;
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
			if (calculateDaysBetween(patients.get(i).getDeadLine()) < 5) {
				multiplicator = 1.5;
			} else if (calculateDaysBetween(patients.get(i).getDeadLine()) < 10) {
				multiplicator = 1.2;
			} else {
				multiplicator = 1;
			}
		}
		return multiplicator;
	}

	public double calculateScore(double timeWindowIndividualCounter, double vCapacityIndividualCounter, int size,
			double multiplicator, List<Patient> patients) {
		
		double repeatCounter = 0;
		for(int i=1;i<patients.size()-1;i++) {
			if(patients.get(i).getAdress().isDepot())
				repeatCounter += 10000.0;
			for(int j=1;j<patients.size()-1;j++) {
				if(i!=j) {
					if(patients.get(i).getName()==patients.get(j).getName()) {
						repeatCounter += 10000.0;
					}
				}	
			}
		}
		
		return ((((size * 1000) + timeWindowIndividualCounter + (vCapacityIndividualCounter * 10)) * multiplicator)-repeatCounter);
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
			matrixTravelDistance[result.getOriginIndex()][result.getDestinationIndex()] = result.getTravelDistance();
			matrixTimeTravel[result.getOriginIndex()][result.getDestinationIndex()] = result.getTravelDuration()
					* 0.01945;
		}
	}

	private int calculateDaysBetween(LocalDate deadline) {

		return (int) ChronoUnit.DAYS.between(LocalDate.now(), deadline);

	}

	public static void main(String[] args) {
		AGServiceImpl service = new AGServiceImpl();
		// service.generateFirstGenerationRandom(20, 200, 100);
	}

}
