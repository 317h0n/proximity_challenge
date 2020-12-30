package com.xyz.models.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.xyz.common.Util;

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
	@JoinColumn(name="tipo_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ApiModelProperty(value = "Type for the machine", required = true)
	private ExpendedoraTipo tipo;

	//@JsonIgnore
	@ApiModelProperty(value = "Password for the machine", required = true)
	private String clave;
	
	@OneToMany(mappedBy = "expendedora", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	@ApiModelProperty(value = "Sales of the machine", required = true)
	private Set<Venta> ventas;
	
	@OneToMany(mappedBy = "expendedora", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	@ApiModelProperty(value = "Withdraws of the machine", required = true)
	private Set<Retiro> retiros;
	
	@ApiModelProperty(value = "To see if the machine has money to collect", required = true)
	@Column(name="recolectar_dinero")
	private boolean recolectarDinero;

	public Expendedora() {
		ventas = new LinkedHashSet<Venta>();
		retiros = new LinkedHashSet<Retiro>();
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

	public Set<Venta> getVentas() {
		return ventas;
	}

	public void setVentas(Set<Venta> ventas) {
		this.ventas = ventas;
	}
	
	public Set<Retiro> getRetiros() {
		return retiros;
	}

	public void setRetiros(Set<Retiro> retiros) {
		this.retiros = retiros;
	}

	public boolean isRecolectarDinero() {
		return recolectarDinero;
	}

	public void setRecolectarDinero(boolean recolectarDinero) {
		this.recolectarDinero = recolectarDinero;
	}

	public void addVenta(Venta venta) {
		this.ventas.add(venta);
	}

	public void addRetiro(Retiro retiro) {
		this.retiros.add(retiro);
	}

	public Double getTotalVenta() {
		Double total = 0.0;
		for (Venta venta : ventas) {
			if (venta.isPagada()) {
				total += venta.getTotal();	
			}
		}
		return Util.round(total, 2);
	}

	public Double getTotalDinero() {
		Double total = 0.0;
		for (Venta venta : ventas) {
			if (venta.getFormaPago().equals("E") && venta.isPagada()) {
				total += venta.getTotal();	
			}
		}
		return Util.round(total, 2);
	}

	public Double getTotalRetiros() {
		Double total = 0.0;
		for (Retiro retiro : retiros) {
			total += retiro.getMonto();
		}
		return Util.round(total, 2);
	}
	
	public Double getDineroDisponible() {
		return Util.round(getTotalDinero() - getTotalRetiros(), 2);
	}
}
