package br.com.univali.healthroutes.api.route.model;

import java.util.Date;

public class Departure{
	private Date time;
    private Place place;
    public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Place getPlace() {
		return place;
	}
	public void setPlace(Place place) {
		this.place = place;
	}
	
}
