package com.xyz.models.entity;

import java.io.Serializable;

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
@Table(name = "items", uniqueConstraints = { @UniqueConstraint(columnNames = { "codigo" }) })
@ApiModel("Model Item")
public class Item implements Serializable {

	private static final long serialVersionUID = 8134221695737438137L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "Id of the model", required = true)
	private Long id;

	@ApiModelProperty(value = "Code for the item", required = true)
	private String codigo;

	@ApiModelProperty(value = "Name for the item", required = true)
	private String nombre;

	@ApiModelProperty(value = "Description for the item", required = true)
	private String descripcion;

	@ApiModelProperty(value = "Price for the item", required = true)
	private Double precio;

	public Item() {
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
