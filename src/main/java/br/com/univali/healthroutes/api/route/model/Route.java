package br.com.univali.healthroutes.api.route.model;

import java.util.List;

public class Route{
    private String id;
    private List<Section> sections;
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Section> getSections() {
		return sections;
	}
	public void setSections(List<Section> sections) {
		this.sections = sections;
	}
	
}
