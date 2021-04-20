package br.com.univali.healthroutes.api.adress.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "adress")
@AllArgsConstructor
@NoArgsConstructor
public class Adress {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_adress;
	
	@Column(name = "street")
	private String street;
	
	@Column(name = "\"number\"")
	private int number;
	
	@Column(name = "district")
	private String district; 
	
	@Column(name = "county")
	private String county;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "latitude")
	private double latitude;
	
	@Column(name = "longitude")
	private double longitude;
	
	@Column(name = "depot")
	private boolean depot = false;
	
	@Column(name = "createDate")
	private LocalDate createDate;

	public Adress(Long id_adress, String street, int number, String district, String county, String state,
			double latitude, double longitude, LocalDate createDate) {
		super();
		this.id_adress = id_adress;
		this.street = street;
		this.number = number;
		this.district = district;
		this.county = county;
		this.state = state;
		this.latitude = latitude;
		this.longitude = longitude;
		this.createDate = createDate;
	}
	
	
	
}
