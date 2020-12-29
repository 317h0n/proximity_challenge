package com.xyz.services;

import com.xyz.models.entity.Item;
import com.xyz.responses.MyApiResponse;
import com.xyz.responses.MyListApiResponse;

public interface IItemService {

	MyListApiResponse<Item> findAll(int pagina, int cantidad, String orderBy);
	MyListApiResponse<Item> findByNombreIgnoreCaseContaining(String nombre, int pagina, int cantidad, String orderBy);
	MyListApiResponse<Item> findByDescripcionIgnoreCaseContaining(String descripcion, int pagina, int cantidad, String orderBy);
	MyApiResponse<Item> findById(Long id);
	MyApiResponse<Item> save(Item item);
	void deleteById(Long id);
}
