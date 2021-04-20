package br.com.univali.healthroutes.api.route.model;

public class Location{
    private double lat;
    public double lng;
    private double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	
}
