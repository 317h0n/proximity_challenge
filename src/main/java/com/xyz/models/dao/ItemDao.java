package com.xyz.models.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyz.models.entity.Item;

public interface ItemDao extends PagingAndSortingRepository<Item, Long>  {

	Page<Item> findAll(Pageable pageable);
	Page<Item> findByNombreIgnoreCaseContaining(String name, Pageable pageable);
	Page<Item> findByDescripcionIgnoreCaseContaining(String descripcion, Pageable pageable);
}
