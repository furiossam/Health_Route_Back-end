package br.com.univali.healthroutes.api.AG.model;

import java.util.List;

import br.com.univali.healthroutes.api.patient.model.Patient;

public class Individual {
	private List<Patient> genotype;
	private double score;
	private List<Integer> indexes;

	public Individual(List<Patient> genotype, double score, List<Integer> indexes) {
		super();
		this.indexes = indexes;
		this.genotype = genotype;
		this.score = score;
	}

	public List<Integer> getIndexes() {
		return indexes;
	}

	public void setIndexes(List<Integer> indexes) {
		this.indexes = indexes;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public List<Patient> getGenotype() {
		return genotype;
	}

	public void setGenotype(List<Patient> genotype) {
		this.genotype = genotype;
	}

	public double getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
