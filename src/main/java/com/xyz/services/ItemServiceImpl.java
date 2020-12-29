package com.xyz.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xyz.models.dao.ItemDao;
import com.xyz.models.entity.Item;
import com.xyz.responses.MyApiResponse;
import com.xyz.responses.MyListApiResponse;

@Service
public class ItemServiceImpl implements IItemService {

	@Autowired
	private ItemDao dao;
	
	private Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);
	
	@Override
	public MyListApiResponse<Item> findAll(int pagina, int cantidad, String orderBy) {
		orderBy = orderBy == null ? "nombre" : orderBy;
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
		MyListApiResponse<Item> response = new MyListApiResponse<Item>();
		try {
			Pageable paging = PageRequest.of(pagina, cantidad, Sort.by(direction, orderBy));
			Page<Item> result = dao.findAll(paging);
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
	public MyListApiResponse<Item> findByNombreIgnoreCaseContaining(String nombre, int pagina, int cantidad, String orderBy) {
		orderBy = orderBy == null ? "nombre" : orderBy;
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
		MyListApiResponse<Item> response = new MyListApiResponse<Item>();
		try {
			Pageable paging = PageRequest.of(pagina, cantidad, Sort.by(direction, orderBy));
			Page<Item> result = dao.findByNombreIgnoreCaseContaining(nombre, paging);
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
	public MyListApiResponse<Item> findByDescripcionIgnoreCaseContaining(String descripcion, int pagina, int cantidad, String orderBy) {
		orderBy = orderBy == null ? "nombre" : orderBy;
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
		MyListApiResponse<Item> response = new MyListApiResponse<Item>();
		try {
			Pageable paging = PageRequest.of(pagina, cantidad, Sort.by(direction, orderBy));
			Page<Item> result = dao.findByNombreIgnoreCaseContaining(descripcion, paging);
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
	public MyApiResponse<Item> findById(Long id) {
		Item i = dao.findById(id).orElse(null);
		MyApiResponse<Item> response = new MyApiResponse<>();
		response.setContent(i);
		response.setMessage(null);
		response.setError(false);;
		if (i == null) {
			response.setMessage("Item " + id + " not found");
			response.setError(true);
		}
		return response;
	}

	@Override
	@Transactional
	public MyApiResponse<Item> save(Item item) {
		Item i = dao.save(item);
		MyApiResponse<Item> response = new MyApiResponse<>();
		response.setContent(i);
		response.setMessage(item.getNombre() + " was saved successfully.");
		response.setError(false);
		if (i == null) {
			response.setMessage("There was a problem trying to save the item " + item.getNombre());
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
