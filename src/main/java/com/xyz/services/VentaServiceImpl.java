package com.xyz.services;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xyz.models.dao.VentaDao;
import com.xyz.models.entity.Venta;
import com.xyz.responses.MyApiResponse;
import com.xyz.responses.MyListApiResponse;

@Service
public class VentaServiceImpl implements IVentaService {

	@Autowired
	private VentaDao dao;
	
	private Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);
	
	@Override
	public MyListApiResponse<Venta> findAll(int page, int pageSize, String orderBy) {
		orderBy = orderBy == null ? "fecha" : orderBy;
		Sort.Direction direction;
		if (orderBy.startsWith("-")) {
			direction = Sort.Direction.DESC;
			orderBy = orderBy.replaceAll("-", "");
		} else if (orderBy.startsWith("+")) {
			direction = Sort.Direction.ASC;
			orderBy = orderBy.replaceAll("+", "");
		} else {
			direction = Sort.Direction.ASC;
		}
		MyListApiResponse<Venta> response = new MyListApiResponse<Venta>();
		try {
			Pageable paging = PageRequest.of(page, pageSize, Sort.by(direction, orderBy));
			Page<Venta> result = dao.findAll(paging);
			response.setError(false);
			response.setElements(result.getTotalElements());
			response.setPages(result.getTotalPages());
			response.setPage(result.getNumber() + 1);
			response.setContent(result.getContent());
		} catch (Exception e) {
			if (e.getMessage().contains("could not resolve property")) {
				log.error("Sort value not exists.");
				response.setMessage("Sort value not exists.");
			} else {
				log.error("No sort problem.");
				response.setMessage(e.getMessage());
			}
		}
		return response;
	}

	@Override
	public MyListApiResponse<Venta> findAllByExpendedora(Long expendedoraId, int page, int pageSize, String orderBy) {
		orderBy = orderBy == null ? "fecha" : orderBy;
		Sort.Direction direction;
		if (orderBy.startsWith("-")) {
			direction = Sort.Direction.DESC;
			orderBy = orderBy.replaceAll("-", "");
		} else if (orderBy.startsWith("+")) {
			direction = Sort.Direction.ASC;
			orderBy = orderBy.replaceAll("+", "");
		} else {
			direction = Sort.Direction.ASC;
		}
		MyListApiResponse<Venta> response = new MyListApiResponse<Venta>();
		try {
			Pageable paging = PageRequest.of(page, pageSize, Sort.by(direction, orderBy));
			Page<Venta> result = dao.findByExpendedora(expendedoraId, paging);
			response.setError(false);
			response.setElements(result.getTotalElements());
			response.setPages(result.getTotalPages());
			response.setPage(result.getNumber() + 1);
			response.setContent(result.getContent());
		} catch (Exception e) {
			if (e.getMessage().contains("could not resolve property")) {
				log.error("Sort value not exists.");
				response.setMessage("Sort value not exists.");
			} else {
				log.error("No sort problem.");
				response.setMessage(e.getMessage());
			}
		}
		return response;
	}

	@Override
	public MyListApiResponse<Venta> findAllByExpendedoraAndFecha(Long expendedoraId, Date fecha, int page, int pageSize,
			String orderBy) {
		orderBy = orderBy == null ? "fecha" : orderBy;
		Sort.Direction direction;
		if (orderBy.startsWith("-")) {
			direction = Sort.Direction.DESC;
			orderBy = orderBy.replaceAll("-", "");
		} else if (orderBy.startsWith("+")) {
			direction = Sort.Direction.ASC;
			orderBy = orderBy.replaceAll("+", "");
		} else {
			direction = Sort.Direction.ASC;
		}
		MyListApiResponse<Venta> response = new MyListApiResponse<Venta>();
		try {
			Pageable paging = PageRequest.of(page, pageSize, Sort.by(direction, orderBy));
			Page<Venta> result = dao.findByExpendedoraAndFecha(expendedoraId, fecha, paging);
			response.setError(false);
			response.setElements(result.getTotalElements());
			response.setPages(result.getTotalPages());
			response.setPage(result.getNumber() + 1);
			response.setContent(result.getContent());
		} catch (Exception e) {
			if (e.getMessage().contains("could not resolve property")) {
				log.error("Sort value not exists.");
				response.setMessage("Sort value not exists.");
			} else {
				log.error("No sort problem.");
				response.setMessage(e.getMessage());
			}
		}
		return response;
	}

	@Override
	public MyApiResponse<Venta> findById(Long id) {
		Venta v = dao.findById(id).orElse(null);
		MyApiResponse<Venta> response = new MyApiResponse<>();
		response.setContent(v);
		response.setMessage(null);
		response.setError(false);;
		if (v == null) {
			response.setMessage("Sale " + id + " not found");
			response.setError(true);
		}
		return response;
	}

	@Override
	@Transactional
	public MyApiResponse<Venta> save(Venta venta) {
		Venta v = dao.save(venta);
		MyApiResponse<Venta> response = new MyApiResponse<>();
		response.setContent(v);
		response.setMessage("Sale was saved successfully.");
		response.setError(false);
		if (v == null) {
			response.setMessage("There was a problem trying to save the sale " + venta.getExpendedora().getCodigo());
			response.setError(true);
		}
		return response;
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		dao.deleteById(id);
	}

}
