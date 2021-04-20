package br.com.univali.healthroutes.api.geo.model;

import java.util.List;

public class Resource{
	private String __type;
	private List<Double> bbox;
	private String name;
	private Point point;
	private Address address;
	private String confidence;
	private String entityType;
	private List<GeocodePoint> geocodePoints;
	private List<String> matchCodes;
    
	public String get__type() {
		return __type;
	}

	public void set__type(String __type) {
		this.__type = __type;
	}

	public List<Double> getBbox() {
		return bbox;
	}

	public void setBbox(List<Double> bbox) {
		this.bbox = bbox;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getConfidence() {
		return confidence;
	}

	public void setConfidence(String confidence) {
		this.confidence = confidence;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public List<GeocodePoint> getGeocodePoints() {
		return geocodePoints;
	}

	public void setGeocodePoints(List<GeocodePoint> geocodePoints) {
		this.geocodePoints = geocodePoints;
	}

	public List<String> getMatchCodes() {
		return matchCodes;
	}

	public void setMatchCodes(List<String> matchCodes) {
		this.matchCodes = matchCodes;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

    public Point getPoint() {
		return point;
	}
}
