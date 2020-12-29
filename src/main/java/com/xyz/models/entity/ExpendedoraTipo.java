package com.xyz.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "expendedoras_tipo", uniqueConstraints = { @UniqueConstraint(columnNames = { "codigo" }) })
@ApiModel("Model ExpendedoraTipo")
public class ExpendedoraTipo implements Serializable {

	private static final long serialVersionUID = 6766736799986694859L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "Id of the model", required = true)
	private Long id;

	@ApiModelProperty(value = "Code for the type of machine", required = true)
	private String codigo;
	
	@ApiModelProperty(value = "Pay method of the machine", required = true)
	@Column(name = "forma_pago")
	private String formaPago;

	public ExpendedoraTipo() {
		super();
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

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
}
