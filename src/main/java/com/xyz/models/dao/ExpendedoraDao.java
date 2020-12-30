package com.xyz.models.dao;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xyz.models.entity.Expendedora;

public interface ExpendedoraDao extends PagingAndSortingRepository<Expendedora, Long> {

	@Query(value="select e from Expendedora e "
			+ "join fetch e.tipo t "
			+ "left join fetch e.ventas v "
			+ "left join fetch v.items vi "
			+ "left join fetch vi.item i "
			+ "left join fetch e.retiros r "
			+ "left join fetch r.tecnico t ",
			countQuery = "select count(e) from Expendedora e")	
	Page<Expendedora> findAll(Pageable pageable);
	
	@Query(value="select e from Expendedora e "
			+ "join fetch e.tipo t "
			+ "join fetch e.ventas v "
			+ "join fetch v.items vi "
			+ "join fetch vi.item i "
			+ "left join fetch e.retiros r "
			+ "left join fetch r.tecnico t "
			+ "where v.fecha = ?1",
			countQuery = "select count(e) from Expendedora e "
					+ "join e.ventas v "
					+ "where v.fecha = ?1")	
	Page<Expendedora> findByVentaFecha(Date fecha, Pageable pageable);
	
	@Query(value="select e from Expendedora e "
			+ "join fetch e.tipo t "
			+ "join fetch e.retiros r "
			+ "join fetch r.tecnico t "
			+ "left join fetch e.ventas v "
			+ "left join fetch v.items vi "
			+ "left join fetch vi.item i "
			+ "where r.fecha = ?1",
			countQuery = "select count(e) from Expendedora e "
					+ "join e.retiros r "
					+ "where r.fecha = ?1")	
	Page<Expendedora> findByRetiroFecha(Date fecha, Pageable pageable);
	
	@Query(value="select e from Expendedora e "
			+ "join fetch e.tipo t "
			+ "left join fetch e.ventas v "
			+ "left join fetch v.items vi "
			+ "left join fetch vi.item i "
			+ "left join fetch e.retiros r "
			+ "left join fetch r.tecnico t "
			+ "where e.id = ?1")	
	Optional<Expendedora> findById(Long id);
}
