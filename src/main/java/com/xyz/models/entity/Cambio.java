package com.xyz.models.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Model Cambio")
public class Cambio {
	
	@ApiModelProperty(value = "Coin", required = true)
	private String moneda;
	@ApiModelProperty(value = "Numbers of coins to return", required = true)
	private int cantidad;
	
	public Cambio(String moneda, int cantidad) {
		this.moneda = moneda;
		this.cantidad = cantidad;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
}
