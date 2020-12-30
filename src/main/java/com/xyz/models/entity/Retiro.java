package com.xyz.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
//@JsonIdentityInfo(
//		  generator = ObjectIdGenerators.PropertyGenerator.class, 
//		  property = "id")
@Table(name = "retiros")
@ApiModel("Model Retiro")
public class Retiro implements Serializable {

	private static final long serialVersionUID = -4630239900435167595L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "Id of the model", required = true)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "Date of the withdraw", required = true)
	private Date fecha;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@ApiModelProperty(value = "Expendedora Model", required = true)
	private Expendedora expendedora;
	
	@ApiModelProperty(value = "Value of the withdraw", required = true)
	private Double monto;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="tecnico_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ApiModelProperty(value = "Tecnico of the withdraw", required = true)
	private Tecnico tecnico;
	
	@PrePersist
	public void prePersist() {
		fecha = new Date();
	}

	public Retiro() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Expendedora getExpendedora() {
		return expendedora;
	}

	public void setExpendedora(Expendedora expendedora) {
		this.expendedora = expendedora;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public Tecnico getTecnico() {
		return tecnico;
	}

	public void setTecnico(Tecnico tecnico) {
		this.tecnico = tecnico;
	}
}
