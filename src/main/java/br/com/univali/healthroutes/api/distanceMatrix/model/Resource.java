package br.com.univali.healthroutes.api.distanceMatrix.model;

import java.util.List;

public class Resource{
    private String __type;
    private List<Destination> destinations;
    private List<Origin> origins;
    private List<Result> results;
    public String get__type() {
		return __type;
	}
	public void set__type(String __type) {
		this.__type = __type;
	}
	public List<Destination> getDestinations() {
		return destinations;
	}
	public void setDestinations(List<Destination> destinations) {
		this.destinations = destinations;
	}
	public List<Origin> getOrigins() {
		return origins;
	}
	public void setOrigins(List<Origin> origins) {
		this.origins = origins;
	}
	public List<Result> getResults() {
		return results;
	}
	public void setResults(List<Result> results) {
		this.results = results;
	}
	
}
