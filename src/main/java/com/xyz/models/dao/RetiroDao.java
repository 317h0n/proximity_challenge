package com.xyz.models.dao;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyz.models.entity.Retiro;

public interface RetiroDao extends PagingAndSortingRepository<Retiro, Long>  {

	@Query(value="select r from Retiro r "
			+ "join fetch r.expendedora e "
			+ "join fetch e.tipo t "
			+ "join fetch r.tecnico t ",
			countQuery = "select count(r) from Retiro r")	
	Page<Retiro> findAll(Pageable pageable);

	@Query(value="select r from Retiro r "
			+ "join fetch r.expendedora e "
			+ "join fetch e.tipo t "
			+ "join fetch r.tecnico t "
			+ "where r.fecha = ?1",
			countQuery = "select count(r) from Retiro r where r.fecha = ?1")	
	Page<Retiro> findByFecha(Date fecha, Pageable pageable);

	@Query(value="select r from Retiro r "
			+ "join fetch r.expendedora e "
			+ "join fetch e.tipo t "
			+ "join fetch r.tecnico t "
			+ "where e.id = ?1",
			countQuery = "select count(r) from Retiro r join r.expendedora e where e.id = ?1")	
	Page<Retiro> findByExpendedora(Long id, Pageable pageable);

	@Query(value="select r from Retiro r "
			+ "join fetch r.expendedora e "
			+ "join fetch e.tipo t "
			+ "join fetch r.tecnico t "
			+ "where e.id = ?1 and r.fecha = ?2",
			countQuery = "select count(r) from Retiro r join r.expendedora e where e.id = ?1 and r.fecha = ?2")	
	Page<Retiro> findByExpendedoraAndFecha(Long id, Date fecha, Pageable pageable);
}
