package br.com.univali.healthroutes.api.route.model;

public class Section{
    private String id;
    private String type;
    private Departure departure;
    private Arrival arrival;
    private Summary summary;
    private Transport transport;
    
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Departure getDeparture() {
		return departure;
	}
	public void setDeparture(Departure departure) {
		this.departure = departure;
	}
	public Arrival getArrival() {
		return arrival;
	}
	public void setArrival(Arrival arrival) {
		this.arrival = arrival;
	}
	public Summary getSummary() {
		return summary;
	}
	public void setSummary(Summary summary) {
		this.summary = summary;
	}
	public Transport getTransport() {
		return transport;
	}
	public void setTransport(Transport transport) {
		this.transport = transport;
	}
	
}
