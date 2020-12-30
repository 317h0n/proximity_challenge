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

import com.xyz.models.dao.ExpendedoraDao;
import com.xyz.models.entity.Expendedora;
import com.xyz.responses.MyApiResponse;
import com.xyz.responses.MyListApiResponse;

@Service
public class ExpendedoraServiceImpl implements IExpendedoraService {

	@Autowired
	private ExpendedoraDao dao;
	
	private Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

	@Override
	public MyListApiResponse<Expendedora> findAll(int page, int pageSize, String orderBy) {
		orderBy = orderBy == null ? "codigo" : orderBy;
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
		MyListApiResponse<Expendedora> response = new MyListApiResponse<Expendedora>();
		try {
			Pageable paging = PageRequest.of(page, pageSize, Sort.by(direction, orderBy));
			Page<Expendedora> result = dao.findAll(paging);
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
	public MyListApiResponse<Expendedora> findAllByVentaFecha(Date fecha, int page, int pageSize, String orderBy) {
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
		MyListApiResponse<Expendedora> response = new MyListApiResponse<Expendedora>();
		try {
			Pageable paging = PageRequest.of(page, pageSize, Sort.by(direction, orderBy));
			Page<Expendedora> result = dao.findByVentaFecha(fecha, paging);
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
	public MyListApiResponse<Expendedora> findAllByRetiroFecha(Date fecha, int page, int pageSize, String orderBy) {
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
		MyListApiResponse<Expendedora> response = new MyListApiResponse<Expendedora>();
		try {
			Pageable paging = PageRequest.of(page, pageSize, Sort.by(direction, orderBy));
			Page<Expendedora> result = dao.findByRetiroFecha(fecha, paging);
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
	public MyApiResponse<Expendedora> findById(Long id) {
		Expendedora e = dao.findById(id).orElse(null);
		MyApiResponse<Expendedora> response = new MyApiResponse<>();
		response.setContent(e);
		response.setMessage(null);
		response.setError(false);;
		if (e == null) {
			response.setMessage("Expendedora " + id + " not found");
			response.setError(true);
		}
		return response;
	}

	@Override
	@Transactional
	public MyApiResponse<Expendedora> save(Expendedora expendedora) {
		Expendedora i = dao.save(expendedora);
		MyApiResponse<Expendedora> response = new MyApiResponse<>();
		response.setContent(i);
		response.setMessage(expendedora.getCodigo() + " was saved successfully.");
		response.setError(false);
		if (i == null) {
			response.setMessage("There was a problem trying to save the expendedora " + expendedora.getCodigo());
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
