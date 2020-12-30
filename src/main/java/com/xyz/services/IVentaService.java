package com.xyz.services;

import java.util.Date;

import com.xyz.models.entity.Venta;
import com.xyz.responses.MyApiResponse;
import com.xyz.responses.MyListApiResponse;

public interface IVentaService {

	MyListApiResponse<Venta> findAll(int page, int pageSize, String orderBy);
	MyListApiResponse<Venta> findAllByExpendedora(Long expendedoraId, int page, int pageSize, String orderBy);
	MyListApiResponse<Venta> findAllByExpendedoraAndFecha(Long expendedoraId, Date fecha, int page, int pageSize, String orderBy);
	MyApiResponse<Venta> findById(Long id);
	MyApiResponse<Venta> save(Venta venta);
	void deleteById(Long id);
}
