package br.com.univali.healthroutes.api.AG.model;

import java.util.List;

import br.com.univali.healthroutes.api.patient.model.Patient;

public class Individual {
	private List<Patient> genotype;
	private double score;
	private List<Integer> indexes;
	private double timeWindow;
	private double distance;

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getTimeWindow() {
		return timeWindow;
	}

	public void setTimeWindow(double timeWindow) {
		this.timeWindow = timeWindow;
	}

	public Individual(List<Patient> genotype, double score, List<Integer> indexes, double timeWindow, double distance) {
		super();
		this.indexes = indexes;
		this.genotype = genotype;
		this.score = score;
		this.timeWindow = timeWindow;
		this.distance = distance;
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
