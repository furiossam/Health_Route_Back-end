package br.com.univali.healthroutes.api.route.model;

public class Place{
    private String type;
    private Location location;
    private OriginalLocation originalLocation;
    public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public OriginalLocation getOriginalLocation() {
		return originalLocation;
	}
	public void setOriginalLocation(OriginalLocation originalLocation) {
		this.originalLocation = originalLocation;
	}
	
}
