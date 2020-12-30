package com.xyz.services;

import com.xyz.models.entity.Tecnico;
import com.xyz.responses.MyApiResponse;
import com.xyz.responses.MyListApiResponse;

public interface ITecnicoService {
	
	MyListApiResponse<Tecnico> findAll(int pagina, int cantidad, String orderBy);
	MyListApiResponse<Tecnico> findByNombreIgnoreCaseContaining(String nombre, int pagina, int cantidad, String orderBy);
	MyApiResponse<Tecnico> findById(Long id);
	MyApiResponse<Tecnico> save(Tecnico tecnico);
	void deleteById(Long id);
}
