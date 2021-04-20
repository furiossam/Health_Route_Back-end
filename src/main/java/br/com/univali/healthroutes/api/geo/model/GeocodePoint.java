package br.com.univali.healthroutes.api.geo.model;

import java.util.List;

public class GeocodePoint{
	private String type;
	private List<Double> coordinates;
	private String calculationMethod;
	private List<String> usageTypes;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Double> getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(List<Double> coordinates) {
		this.coordinates = coordinates;
	}
	public String getCalculationMethod() {
		return calculationMethod;
	}
	public void setCalculationMethod(String calculationMethod) {
		this.calculationMethod = calculationMethod;
	}
	public List<String> getUsageTypes() {
		return usageTypes;
	}
	public void setUsageTypes(List<String> usageTypes) {
		this.usageTypes = usageTypes;
	}
	
}
