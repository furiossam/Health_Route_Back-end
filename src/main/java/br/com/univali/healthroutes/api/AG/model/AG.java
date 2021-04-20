package br.com.univali.healthroutes.api.AG.model;

import java.util.List;

public class AG {
	private List<Individual> individuals;
	private double mutation;
	private Enum Selection;
		
	public List<Individual> getIndividuals() {
		return individuals;
	}
	public void setIndividuals(List<Individual> individuals) {
		this.individuals = individuals;
	}
	public double getMutation() {
		return mutation;
	}
	public void setMutation(double mutation) {
		this.mutation = mutation;
	}
	public Enum getSelection() {
		return Selection;
	}
	public void setSelection(Enum selection) {
		Selection = selection;
	}
	
	
	
	
	
	
}
