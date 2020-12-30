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

import com.xyz.models.dao.RetiroDao;
import com.xyz.models.entity.Retiro;
import com.xyz.responses.MyApiResponse;
import com.xyz.responses.MyListApiResponse;

@Service
public class RetiroServiceImpl implements IRetiroService {

	@Autowired
	private RetiroDao dao;
	
	private Logger log = LoggerFactory.getLogger(RetiroServiceImpl.class);

	@Override
	public MyListApiResponse<Retiro> findAll(int page, int pageSize, String orderBy) {
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
		MyListApiResponse<Retiro> response = new MyListApiResponse<Retiro>();
		try {
			Pageable paging = PageRequest.of(page, pageSize, Sort.by(direction, orderBy));
			Page<Retiro> result = dao.findAll(paging);
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
	public MyListApiResponse<Retiro> findAllByExpendedora(Long expendedoraId, int page, int pageSize, String orderBy) {
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
		MyListApiResponse<Retiro> response = new MyListApiResponse<Retiro>();
		try {
			Pageable paging = PageRequest.of(page, pageSize, Sort.by(direction, orderBy));
			Page<Retiro> result = dao.findByExpendedora(expendedoraId, paging);
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
	public MyListApiResponse<Retiro> findAllByExpendedoraAndFecha(Long expendedoraId, Date fecha, int page,
			int pageSize, String orderBy) {
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
		MyListApiResponse<Retiro> response = new MyListApiResponse<Retiro>();
		try {
			Pageable paging = PageRequest.of(page, pageSize, Sort.by(direction, orderBy));
			Page<Retiro> result = dao.findByExpendedoraAndFecha(expendedoraId, fecha, paging);
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
	public MyApiResponse<Retiro> findById(Long id) {
		Retiro r = dao.findById(id).orElse(null);
		MyApiResponse<Retiro> response = new MyApiResponse<>();
		response.setContent(r);
		response.setMessage(null);
		response.setError(false);;
		if (r == null) {
			response.setMessage("Withdraw " + id + " not found");
			response.setError(true);
		}
		return response;
	}

	@Override
	@Transactional
	public MyApiResponse<Retiro> save(Retiro retiro) {
		Retiro r = dao.save(retiro);
		MyApiResponse<Retiro> response = new MyApiResponse<>();
		response.setContent(r);
		response.setMessage("Withdraw was saved successfully.");
		response.setError(false);
		if (r == null) {
			response.setMessage("There was a problem trying to save the withdraw " + retiro.getExpendedora().getCodigo());
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
