package com.xyz.models.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyz.models.entity.Tecnico;

public interface TecnicoDao extends PagingAndSortingRepository<Tecnico, Long>  {

	Page<Tecnico> findAll(Pageable pageable);
	Page<Tecnico> findByNombreIgnoreCaseContaining(String nonbre, Pageable pageable);
}
