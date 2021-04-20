package br.com.univali.healthroutes.api.geo.model;

import java.util.List;

public class ResourceSet{
	private int estimatedTotal;
	private List<Resource> resources;
	
	public int getEstimatedTotal() {
		return estimatedTotal;
	}

	public void setEstimatedTotal(int estimatedTotal) {
		this.estimatedTotal = estimatedTotal;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

    public List<Resource> getResources() {
		return resources;
	}
	
}
