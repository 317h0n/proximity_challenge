package com.xyz.models.dao;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyz.models.entity.Venta;

public interface VentaDao extends PagingAndSortingRepository<Venta, Long>  {

	@Query(value="select v from Venta v "
			+ "join fetch v.expendedora e "
			+ "join fetch e.tipo t "
			+ "left join fetch v.items vi "
			+ "left join fetch vi.item i ",
			countQuery = "select count(v) from Venta v")	
	Page<Venta> findAll(Pageable pageable);

	@Query(value="select v from Venta v "
			+ "join fetch v.expendedora e "
			+ "join fetch e.tipo t "
			+ "left join fetch v.items vi "
			+ "left join fetch vi.item i "
			+ "where v.fecha = ?1",
			countQuery = "select count(v) from Venta v where v.fecha = ?1")	
	Page<Venta> findByFecha(Date fecha, Pageable pageable);

	@Query(value="select v from Venta v "
			+ "join fetch v.expendedora e "
			+ "join fetch e.tipo t "
			+ "left join fetch v.items vi "
			+ "left join fetch vi.item i "
			+ "where e.id = ?1",
			countQuery = "select count(v) from Venta v join v.expendedora e where e.id = ?1")	
	Page<Venta> findByExpendedora(Long id, Pageable pageable);

	@Query(value="select v from Venta v "
			+ "join fetch v.expendedora e "
			+ "join fetch e.tipo t "
			+ "left join fetch v.items vi "
			+ "left join fetch vi.item i "
			+ "where e.id = ?1 and v.fecha = ?2",
			countQuery = "select count(v) from Venta v join v.expendedora e where e.id = ?1 and v.fecha = ?2")	
	Page<Venta> findByExpendedoraAndFecha(Long id, Date fecha, Pageable pageable);
}
