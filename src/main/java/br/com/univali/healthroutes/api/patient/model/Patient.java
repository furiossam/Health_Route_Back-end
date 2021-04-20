package br.com.univali.healthroutes.api.patient.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import br.com.univali.healthroutes.api.adress.model.Adress;
import br.com.univali.healthroutes.api.medicine.model.Medicine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "patient")
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_patient;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="id_adress")	
	@NotNull
	private Adress adress;
	
	@Column(name = "\"name\"")
	private String name;
	
	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "rg")
	private String rg;
	
	@Column(name = "createDate")
	private LocalDate createDate;
	
	@Column(name = "deadline")
	private LocalDate deadLine;
	
	@ManyToMany(cascade = CascadeType.ALL) 
	@JoinColumn(name = "id_patient")
	private List<Medicine> medicines;

	public Patient(Long id_patient, Adress adress, String name, String cpf, String rg, LocalDate createDate,
			LocalDate deadLine) {
		super();
		this.id_patient = id_patient;
		this.adress = adress;
		this.name = name;
		this.cpf = cpf;
		this.rg = rg;
		this.createDate = createDate;
		this.deadLine = deadLine;
	} 
	

}
