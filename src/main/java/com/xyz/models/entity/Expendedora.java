package com.xyz.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "expendedoras")
@ApiModel("Model Expendedora")
public class Expendedora implements Serializable {

	private static final long serialVersionUID = 5246119262032626427L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "Id of the model", required = true)
	private Long id;

	@ApiModelProperty(value = "Code for the machine", required = true)
	private String codigo;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="item_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ApiModelProperty(value = "Type for the machine", required = true)
	private ExpendedoraTipo tipo;

	@ApiModelProperty(value = "Password for the machine", required = true)
	private String clave;

	public Expendedora() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public ExpendedoraTipo getTipo() {
		return tipo;
	}

	public void setTipo(ExpendedoraTipo tipo) {
		this.tipo = tipo;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}
	
}
