package com.xyz.services;

import java.util.Date;

import com.xyz.models.entity.Retiro;
import com.xyz.responses.MyApiResponse;
import com.xyz.responses.MyListApiResponse;

public interface IRetiroService {

	MyListApiResponse<Retiro> findAll(int page, int pageSize, String orderBy);
	MyListApiResponse<Retiro> findAllByExpendedora(Long expendedoraId, int page, int pageSize, String orderBy);
	MyListApiResponse<Retiro> findAllByExpendedoraAndFecha(Long expendedoraId, Date fecha, int page, int pageSize, String orderBy);
	MyApiResponse<Retiro> findById(Long id);
	MyApiResponse<Retiro> save(Retiro retiro);
	void deleteById(Long id);
}
