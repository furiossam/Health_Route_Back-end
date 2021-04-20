package br.com.univali.healthroutes.api.geo.model;

public class Address{
	private String addressLine;
	private String adminDistrict;
	private String countryRegion;
	private String formattedAddress;
	private String locality;
	private String postalCode;
	
	public String getAddressLine() {
		return addressLine;
	}
	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}
	public String getAdminDistrict() {
		return adminDistrict;
	}
	public void setAdminDistrict(String adminDistrict) {
		this.adminDistrict = adminDistrict;
	}
	public String getCountryRegion() {
		return countryRegion;
	}
	public void setCountryRegion(String countryRegion) {
		this.countryRegion = countryRegion;
	}
	public String getFormattedAddress() {
		return formattedAddress;
	}
	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
}
