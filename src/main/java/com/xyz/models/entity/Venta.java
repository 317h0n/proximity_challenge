package com.xyz.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
@Table(name = "ventas")
@ApiModel("Model Venta")
public class Venta implements Serializable {

	private static final long serialVersionUID = 1688285570337516302L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "Id of the model", required = true)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "Date of the sale", required = true)
	private Date fecha;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private Expendedora expendedora;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "venta_id")
	@ApiModelProperty(value = "Items of the sale", required = true)
	private List<VentaItem> items;
	
	@ApiModelProperty(value = "Pay method of the sale", required = true)
	@Column(name = "forma_pago")
	private String formaPago;
	
	@PrePersist
	public void prePersist() {
		fecha = new Date();
	}

	public Venta() {
		this.items = new ArrayList<>();
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

	public List<VentaItem> getItems() {
		return items;
	}

	public void setItems(List<VentaItem> items) {
		this.items = items;
	}
	
	public void addItemFactura(VentaItem item) {
		this.items.add(item);
	}

	public Double getTotal() {
		Double total = 0.0;
		int size = items.size();
		for (int i = 0; i < size; i++) {
			total += items.get(i).calcularImporte();
		}
		return total;
	}

	public Expendedora getExpendedora() {
		return expendedora;
	}

	public void setExpendedora(Expendedora expendedora) {
		this.expendedora = expendedora;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
}
