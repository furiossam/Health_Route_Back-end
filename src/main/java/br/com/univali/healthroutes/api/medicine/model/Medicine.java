package br.com.univali.healthroutes.api.medicine.model;



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
@Table(name = "medicine")
@AllArgsConstructor
@NoArgsConstructor
public class Medicine{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_medicine;
	
	@Column(name = "\"name\"")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "thermolabile")
	private Boolean thermolabile;
	
	@Column(name = "createDate")
	private LocalDate createDate;

}
