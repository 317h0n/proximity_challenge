package com.xyz.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyz.models.entity.Expendedora;
import com.xyz.models.entity.Retiro;
import com.xyz.models.entity.Tecnico;
import com.xyz.responses.MyApiResponse;
import com.xyz.responses.MyListApiResponse;
import com.xyz.services.IExpendedoraService;
import com.xyz.services.IRetiroService;
import com.xyz.services.ITecnicoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@ApiRestController
@Api(value = "Service for the Expendedoras resource")
public class ExpendedoraController {

	private Logger log = LoggerFactory.getLogger(ExpendedoraController.class);
	final ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private IExpendedoraService service;
	
	@Autowired
	private ITecnicoService tecnicoService;
	
	@Autowired
	private IRetiroService retiroService;
	
	@Value("${expendedoras.withdraw.value}")
	private Double montoRetiro;
	
	@GetMapping("/expendedoras")
	@ApiOperation(value = "Get expendedoras", notes = "Return all expendedoras" )
	@ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                value = "Results page you want to retrieve (1..N)", defaultValue = "1"),
        @ApiImplicitParam(name = "page_size", dataType = "integer", paramType = "query",
                value = "Number of records per page.", defaultValue = "10"),
        @ApiImplicitParam(name = "order_by", dataType = "string", paramType = "query",
                value = "Sorting criteria in the format property. " +
                        "Default sort order is ascending. " +
                        "For descendig sort order use a - before property", defaultValue = "codigo")
	})
	public ResponseEntity<MyListApiResponse<Expendedora>> index(
			@RequestParam(name = "page", defaultValue = "1", required = false) int page
			, @RequestParam(name = "page_size", defaultValue = "10", required = false) int pageSize
			, @RequestParam(name = "order_by", defaultValue = "codigo", required = false) String orderBy) {
		MyListApiResponse<Expendedora> response;
		try {
			page = page < 1 ? 1 : page;
			pageSize = pageSize < 1 ? 1 : pageSize;
			response = service.findAll(--page, pageSize, orderBy);			
		} catch (DataAccessException e) {
			response = new MyListApiResponse<Expendedora>();
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
	
	@GetMapping("/expendedoras/sales/{fecha}")
	@ApiOperation(value = "Get expendedoras by sales' date", notes = "Return all expendedoras by sales' date" )
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fecha", dataType = "date", paramType = "path",
                value = "Date in format yyyy-MM-dd", required = true),
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                value = "Results page you want to retrieve (1..N)", defaultValue = "1"),
        @ApiImplicitParam(name = "page_size", dataType = "integer", paramType = "query",
                value = "Number of records per page.", defaultValue = "10"),
        @ApiImplicitParam(name = "order_by", dataType = "string", paramType = "query",
                value = "Sorting criteria in the format property. " +
                        "Default sort order is ascending. " +
                        "For descendig sort order use a - before property", defaultValue = "codigo")
	})
	public ResponseEntity<MyListApiResponse<Expendedora>> getByVentaFecha(
			@PathVariable String fecha
			, @RequestParam(name = "page", defaultValue = "1", required = false) int page
			, @RequestParam(name = "page_size", defaultValue = "10", required = false) int pageSize
			, @RequestParam(name = "order_by", defaultValue = "codigo", required = false) String orderBy) {
		MyListApiResponse<Expendedora> response;
		try {
			page = page < 1 ? 1 : page;
			pageSize = pageSize < 1 ? 1 : pageSize;
			Date f = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
			log.info("Fecha: " + f);
			response = service.findAllByVentaFecha(f, --page, pageSize, orderBy);			
		} catch (DataAccessException e) {
			response = new MyListApiResponse<Expendedora>();
			response.setError(true);
			log.error("error", e);
			response.setMessage("Internal error: ".concat(e.getMostSpecificCause().getMessage()));
		} catch (ParseException e) {
			response = new MyListApiResponse<Expendedora>();
			response.setError(true);
			log.error("error", e);
			response.setMessage("Error: Bad date format, use yyyy-MM-dd format!");
		}
		if (!response.isError()) {
			return new ResponseEntity<>(response, HttpStatus.OK); 
		} else {
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@GetMapping("/expendedoras/withdraw/{fecha}")
	@ApiOperation(value = "Get expendedoras by withdraws' date", notes = "Return all expendedoras by withdraws' date" )
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fecha", dataType = "date", paramType = "path",
                value = "Date in format yyyy-MM-dd", required = true),
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                value = "Results page you want to retrieve (1..N)", defaultValue = "1"),
        @ApiImplicitParam(name = "page_size", dataType = "integer", paramType = "query",
                value = "Number of records per page.", defaultValue = "10"),
        @ApiImplicitParam(name = "order_by", dataType = "string", paramType = "query",
                value = "Sorting criteria in the format property. " +
                        "Default sort order is ascending. " +
                        "For descendig sort order use a - before property", defaultValue = "codigo")
	})
	public ResponseEntity<MyListApiResponse<Expendedora>> getByRetiroFecha(
			@PathVariable String fecha
			, @RequestParam(name = "page", defaultValue = "1", required = false) int page
			, @RequestParam(name = "page_size", defaultValue = "10", required = false) int pageSize
			, @RequestParam(name = "order_by", defaultValue = "codigo", required = false) String orderBy) {
		MyListApiResponse<Expendedora> response;
		try {
			page = page < 1 ? 1 : page;
			pageSize = pageSize < 1 ? 1 : pageSize;
			Date f = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
			log.info("Fecha: " + f);
			response = service.findAllByRetiroFecha(f, --page, pageSize, orderBy);			
		} catch (DataAccessException e) {
			response = new MyListApiResponse<Expendedora>();
			response.setError(true);
			log.error("error", e);
			response.setMessage("Internal error: ".concat(e.getMostSpecificCause().getMessage()));
		} catch (ParseException e) {
			response = new MyListApiResponse<Expendedora>();
			response.setError(true);
			log.error("error", e);
			response.setMessage("Error: Bad date format, use yyyy-MM-dd format!");
		}
		if (!response.isError()) {
			return new ResponseEntity<>(response, HttpStatus.OK); 
		} else {
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@ApiOperation(value = "Get a expendedoras with that id", notes = "Return a expendedoras" )
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", dataType = "integer", paramType = "path",
                value = "ID of the expendedoras", required = true)
	})	
	@GetMapping("/expendedoras/{id}")
	public ResponseEntity<MyApiResponse<Expendedora>> show(
			@PathVariable Long id) {
		MyApiResponse<Expendedora> response = new MyApiResponse<Expendedora>();
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
	
	@ApiOperation(value = "Collect money from the machine", notes = "" )
	@ApiImplicitParams({
		@ApiImplicitParam(name = "expendedoraId", dataType = "int", paramType = "form",
                value = "ID of the machine", required = true),
		@ApiImplicitParam(name = "tecnicoId", dataType = "int", paramType = "form",
        		value = "ID of the person who collects the money", required = true)
	})
	@PostMapping("/expendedoras/withdraw")
	public ResponseEntity<MyApiResponse<Retiro>> collectMoney(
			@RequestParam int expendedoraId
			, @RequestParam int tecnicoId) {
		MyApiResponse<Retiro> response = null;
		try {
			Expendedora e = service.findById(Long.valueOf(expendedoraId)).getContent();
			if (e == null) {
				throw new Exception("Machine with ID " + expendedoraId + " not exists");
			}
			if (e.getDineroDisponible() < montoRetiro) {
				throw new Exception("Machine does not have enough money to collect");
			}
			Tecnico t = tecnicoService.findById(Long.valueOf(tecnicoId)).getContent();
			if (t == null) {
				throw new Exception("Tech with ID " + tecnicoId + " not exists");
			}
			Retiro r = new Retiro();
			r.setExpendedora(e);
			r.setMonto(e.getDineroDisponible());
			r.setTecnico(t);
			response = retiroService.save(r);
			e.setRecolectarDinero(false);
			service.save(e);
		} catch (DataAccessException e) {
			response = new MyApiResponse<Retiro>();
			response.setError(true);
			log.error("error", e);
			response.setMessage("Internal error: ".concat(e.getMostSpecificCause().getMessage()));
		} catch (Exception e) {
			response = new MyApiResponse<Retiro>();
			response.setError(true);
			log.error("error", e);
			response.setMessage("Internal error: ".concat(e.getMessage()));
		} 
		if (!response.isError()) {
			return new ResponseEntity<>(response, HttpStatus.OK); 
		} else {
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
}
