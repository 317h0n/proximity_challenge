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
@Table(name = "ventas_items")
@ApiModel("Model Venta - Item")
public class VentaItem implements Serializable {

	private static final long serialVersionUID = -9097792314337526343L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "Id of the model", required = true)
	private Long id;

	@ApiModelProperty(value = "Number of items", required = true)
	private Integer cantidad;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="item_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ApiModelProperty(value = "Item of the sale", required = true)
	private Item item;

	public VentaItem() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Double calcularImporte() {
		return cantidad.doubleValue() * item.getPrecio();
	}
}
