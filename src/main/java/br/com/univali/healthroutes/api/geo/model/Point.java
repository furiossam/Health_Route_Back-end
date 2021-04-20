package br.com.univali.healthroutes.api.geo.model;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString), Root.class); */
public class Point{
	private String type;
	private List<Double> coordinates;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCoordinates(List<Double> coordinates) {
		this.coordinates = coordinates;
	}
	
	public List<Double> getCoordinates() {
		return coordinates;
	}
	
	 
}

