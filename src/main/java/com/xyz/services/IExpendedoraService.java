package com.xyz.services;

import java.util.Date;

import com.xyz.models.entity.Expendedora;
import com.xyz.responses.MyApiResponse;
import com.xyz.responses.MyListApiResponse;

public interface IExpendedoraService {

	MyListApiResponse<Expendedora> findAll(int page, int pageSize, String orderBy);
	MyListApiResponse<Expendedora> findAllByVentaFecha(Date fecha, int page, int pageSize, String orderBy);
	MyListApiResponse<Expendedora> findAllByRetiroFecha(Date fecha, int page, int pageSize, String orderBy);
	MyApiResponse<Expendedora> findById(Long id);
	MyApiResponse<Expendedora> save(Expendedora expendedora);
	void deleteById(Long id);
}
