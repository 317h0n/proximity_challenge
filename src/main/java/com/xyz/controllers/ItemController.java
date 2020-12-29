package com.xyz.controllers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyz.models.entity.Item;
import com.xyz.responses.MyApiResponse;
import com.xyz.responses.MyListApiResponse;
import com.xyz.services.IItemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@ApiRestController
@Api(value = "Service for the Items resource")
public class ItemController {

	private Logger log = LoggerFactory.getLogger(ItemController.class);
	final ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private IItemService service;
	
	@GetMapping("/items")
	@ApiOperation(value = "Get items", notes = "Return all items" )
	@ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                value = "Results page you want to retrieve (1..N)", defaultValue = "1"),
        @ApiImplicitParam(name = "page_size", dataType = "integer", paramType = "query",
                value = "Number of records per page.", defaultValue = "10"),
        @ApiImplicitParam(name = "order_by", dataType = "string", paramType = "query",
                value = "Sorting criteria in the format property. " +
                        "Default sort order is ascending. " +
                        "For descendig sort order use a - before property", defaultValue = "nombre")
	})
	public ResponseEntity<MyListApiResponse<Item>> index(
			@RequestParam(name = "page", defaultValue = "1", required = false) int page
			, @RequestParam(name = "page_size", defaultValue = "10", required = false) int pageSize
			, @RequestParam(name = "order_by", defaultValue = "nombre", required = false) String orderBy) {
		MyListApiResponse<Item> response;
		try {
			page = page < 1 ? 1 : page;
			pageSize = pageSize < 1 ? 1 : pageSize;
			response = service.findAll(--page, pageSize, orderBy);			
		} catch (DataAccessException e) {
			response = new MyListApiResponse<Item>();
			response.setError(true);
			log.error("error", e);
			response.setMessage("Internal error: ".concat(e.getMostSpecificCause().getMessage()));
		}
		if (!response.isError()) {
			return new ResponseEntity<>(response, HttpStatus.OK); 
		} else {
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@GetMapping("/items/search/{nombre}")
	@ApiOperation(value = "Search items", notes = "Return all items by name" )
	@ApiImplicitParams({
        @ApiImplicitParam(name = "nombre", dataType = "string", paramType = "path",
                value = "Search key", required = true),
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                value = "Results page you want to retrieve (1..N)", defaultValue = "1"),
        @ApiImplicitParam(name = "page_size", dataType = "integer", paramType = "query",
                value = "Number of records per page.", defaultValue = "10"),
        @ApiImplicitParam(name = "order_by", dataType = "string", paramType = "query",
                value = "Sorting criteria in the format property. " +
                        "Default sort order is ascending. " +
                        "For descendig sort order use a - before property", defaultValue = "nombre")
	})
	public ResponseEntity<MyListApiResponse<Item>> search(
			@PathVariable String nombre
			, @RequestParam(name = "page", defaultValue = "1", required = false) int page
			, @RequestParam(name = "page_size", defaultValue = "10", required = false) int pageSize
			, @RequestParam(name = "order_by", defaultValue = "nombre", required = false) String orderBy) {
		MyListApiResponse<Item> response;
		try {
			page = page < 1 ? 1 : page;
			pageSize = pageSize < 1 ? 1 : pageSize;
			response = service.findByNombreIgnoreCaseContaining(nombre, --page, pageSize, orderBy);			
		} catch (DataAccessException e) {
			response = new MyListApiResponse<Item>();
			response.setError(true);
			log.error("error", e);
			response.setMessage("Internal error: ".concat(e.getMostSpecificCause().getMessage()));
		}
		if (!response.isError()) {
			return new ResponseEntity<>(response, HttpStatus.OK); 
		} else {
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@ApiOperation(value = "Get a item with that id", notes = "Return a item" )
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", dataType = "integer", paramType = "path",
                value = "ID of the item", required = true)
	})	
	@GetMapping("/items/{id}")
	public ResponseEntity<MyApiResponse<Item>> show(
			@PathVariable Long id) {
		MyApiResponse<Item> response = new MyApiResponse<Item>();
		try {
			response = service.findById(id);
		} catch (DataAccessException e) {
			response.setError(true);
			response.setMessage("Internal error: ".concat(e.getMostSpecificCause().getMessage()));
		}
		if (!response.isError()) {
			return new ResponseEntity<>(response, HttpStatus.OK); 
		} else {
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); 
		}
	}
	
	@ApiOperation(value = "Create a item", notes = "Return created item")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "item", dataType = "Item", value = "Item to create", required = true),
	})
	@PostMapping("/items")
	public ResponseEntity<MyApiResponse<Item>> create(@RequestBody Item item) {
		MyApiResponse<Item> response;
		Map<String, String> errors = validateItem(item);
		if (!errors.isEmpty()) {
			response = new MyApiResponse<Item>();
			response.setError(true);
			Map.Entry<String, String> entry = errors.entrySet().iterator().next();
			String key = entry.getKey();
			String value = entry.getValue();
			response.setMessage("There is a problem with " + key + ", " + value);
		} else {
			response = service.save(item);			
		}
		if (!response.isError()) {
			return new ResponseEntity<>(response, HttpStatus.OK); 
		} else {
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@ApiOperation(value = "Update a item", notes = "Return updated item")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "item", dataType = "Item", value = "Item to update", required = true),
		@ApiImplicitParam(name = "id", dataType = "integer", paramType = "path", value = "ID of the item", required = true)
	})
	@PutMapping("/items/{id}")
	public ResponseEntity<MyApiResponse<Item>> update(@RequestBody Item item, @PathVariable Long id) {
		MyApiResponse<Item> response;
		Map<String, String> errors = validateItem(item);
		if (!errors.isEmpty()) {
			response = new MyApiResponse<Item>();
			response.setError(true);
			Map.Entry<String, String> entry = errors.entrySet().iterator().next();
			String key = entry.getKey();
			String value = entry.getValue();
			response.setMessage("There is a problem with " + key + ", " + value);
		} else {
			response = service.findById(id);
			if (response.getContent() != null) {
				Item i = response.getContent();
				i.setCodigo(item.getCodigo());
				i.setNombre(item.getNombre());
				i.setDescripcion(item.getDescripcion());
				i.setPrecio(item.getPrecio());
				response = service.save(i);
			}
		}
		if (!response.isError()) {
			return new ResponseEntity<>(response, HttpStatus.OK); 
		} else {
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@ApiOperation(value = "Update properties of a item", notes = "Return updated item")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "item", dataType = "Object", value = "Properties to update", required = true),
			@ApiImplicitParam(name = "id", dataType = "integer", paramType = "path", value = "ID of the item", required = true)
	})
	@PatchMapping("/items/{id}")
	public ResponseEntity<MyApiResponse<Item>> updatePartial(@RequestBody Map<String, Object> item
			, @PathVariable Long id) {
		MyApiResponse<Item> response;
		response = service.findById(id);
		if (response.getContent() != null) {
			Item i = response.getContent();
			item.forEach((change, value) -> {
				switch (change) {
				case "nombre":
					i.setNombre((String) value);
					break;
				case "descripcion":
					i.setDescripcion((String) value);
					break;
				case "codigo":
					i.setCodigo((String) value);
					break;
				case "precio":
					i.setPrecio((Double) value);
					break;
				}
			});
			response = service.save(i);
		}
		if (!response.isError()) {
			return new ResponseEntity<>(response, HttpStatus.OK); 
		} else {
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@ApiOperation(value = "Delete a item", notes = "Nothing to return")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", dataType = "integer", paramType = "path",
        		value = "ID of the item", required = true)
	})
	@DeleteMapping("/items/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		MyApiResponse<Item> response;
		try {
			response = service.findById(id);
			if (response.getContent() != null) {
				service.deleteById(id);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
		} catch (DataAccessException e) {
			response = new MyApiResponse<Item>();
			response.setContent(null);
			response.setError(true);
			response.setMessage("Internal error: ".concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private Map<String, String> validateItem(Item item) {
		Map<String, String> errors = new HashMap<>();
		if (item.getNombre() == null || item.getNombre().isEmpty()) {
			errors.put("nombre", "Nombre cannot be empty");
		}
		if (item.getDescripcion() == null || item.getDescripcion().isEmpty()) {
			errors.put("descripcion", "Descripcion cannot be empty");
		}
		if (item.getCodigo() == null || item.getCodigo().isEmpty()) {
			errors.put("codigo", "Codigo cannot be empty");
		}
		if (item.getPrecio() == null || item.getPrecio() <= 0) {
			errors.put("precio", "Precio needs to be greater than 0");
		}		
		return errors;
	}
}
