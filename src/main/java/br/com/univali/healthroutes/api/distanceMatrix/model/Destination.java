package br.com.univali.healthroutes.api.distanceMatrix.model;

public class Destination{
	private double longitude;
    private double latitude;
    
    public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
