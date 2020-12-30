package com.xyz.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyz.common.Util;
import com.xyz.models.entity.Cambio;
import com.xyz.models.entity.Expendedora;
import com.xyz.models.entity.Item;
import com.xyz.models.entity.Venta;
import com.xyz.models.entity.VentaItem;
import com.xyz.responses.MyApiResponse;
import com.xyz.responses.MyListApiResponse;
import com.xyz.responses.MyListNoPagingApiResponse;
import com.xyz.services.IExpendedoraService;
import com.xyz.services.IItemService;
import com.xyz.services.IVentaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@ApiRestController
@Api(value = "Service for the Ventas resource")
public class VentaController {

	private Logger log = LoggerFactory.getLogger(VentaController.class);
	
	final ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private IVentaService service;
	
	@Autowired
	private IExpendedoraService expendedoraService;
	
	@Autowired
	private IItemService itemService;
	
	@Value("#{'${expendedoras.billetes}'.split(',')}")
	private List<Double> billetes;
	
	@Value("#{'${expendedoras.monedas}'.split(',')}")
	private List<Double> monedas;
	
	@Value("${expendedoras.withdraw.value}")
	private Double montoRetiro;
	
	@ApiOperation(value = "Get sales", notes = "Return all sales" )
	@ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                value = "Results page you want to retrieve (1..N)", defaultValue = "1"),
        @ApiImplicitParam(name = "page_size", dataType = "integer", paramType = "query",
                value = "Number of records per page.", defaultValue = "10"),
        @ApiImplicitParam(name = "order_by", dataType = "string", paramType = "query",
                value = "Sorting criteria in the format property. " +
                        "Default sort order is ascending. " +
                        "For descendig sort order use a - before property", defaultValue = "fecha")
	})
	@GetMapping("/ventas")
	public ResponseEntity<MyListApiResponse<Venta>> index(
			@RequestParam(name = "page", defaultValue = "1", required = false) int page
			, @RequestParam(name = "page_size", defaultValue = "10", required = false) int pageSize
			, @RequestParam(name = "order_by", defaultValue = "fecha", required = false) String orderBy) {
		MyListApiResponse<Venta> response;
		try {
			page = page < 1 ? 1 : page;
			pageSize = pageSize < 1 ? 1 : pageSize;
			response = service.findAll(--page, pageSize, orderBy);			
		} catch (DataAccessException e) {
			response = new MyListApiResponse<Venta>();
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
	
	@ApiOperation(value = "Get a sale with that id", notes = "Return a sale" )
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", dataType = "integer", paramType = "path",
                value = "ID of the sale", required = true)
	})	
	@GetMapping("/ventas/{id}")
	public ResponseEntity<MyApiResponse<Venta>> show(
			@PathVariable Long id) {
		MyApiResponse<Venta> response = new MyApiResponse<Venta>();
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
	
	@ApiOperation(value = "Create a sale", notes = "Return created sale")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "venta", dataType = "Venta.class", value = "Sale to create", required = true),
	})
	@PostMapping("/ventas")
	public ResponseEntity<MyApiResponse<Venta>> create(@RequestBody Venta venta) {
		MyApiResponse<Venta> response;
		Map<String, String> errors = validateVenta(venta);
		if (!errors.isEmpty()) {
			response = new MyApiResponse<Venta>();
			response.setError(true);
			Map.Entry<String, String> entry = errors.entrySet().iterator().next();
			String key = entry.getKey();
			String value = entry.getValue();
			response.setMessage("There is a problem with " + key + ", " + value);
		} else {
			Expendedora e = expendedoraService.findById(venta.getExpendedora().getId()).getContent();
			if (e == null) {
				response = new MyApiResponse<Venta>();
				response.setError(true);
				response.setMessage("Expendedora with ID " + venta.getExpendedora().getId() + " not exists");
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			Venta v = new Venta();
			v.setExpendedora(e);
			v.setFormaPago(venta.getFormaPago());
			for (VentaItem vi : venta.getItems()) {
				Item i = itemService.findById(vi.getItem().getId()).getContent();
				if (i == null) {
					response = new MyApiResponse<Venta>();
					response.setError(true);
					response.setMessage("Item with ID " + vi.getItem().getId() + " not exists");
					return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
				}
				VentaItem lineaVenta = new VentaItem();
				lineaVenta.setCantidad(vi.getCantidad());
				lineaVenta.setItem(i);
				v.addVentaItem(lineaVenta);
			}
			v.setPagada(false);
			response = service.save(v);			
		}
		if (!response.isError()) {
			return new ResponseEntity<>(response, HttpStatus.OK); 
		} else {
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@ApiOperation(value = "Pay a sale", notes = "Return a payment")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", dataType = "integer", paramType = "path",
            value = "ID of the sale", required = true)
		, @ApiImplicitParam(name = "formaPago", dataType = "String", paramType = "form",
        value = "Pay Method, it could be: 'E' or 'T' ", required = true)
		, @ApiImplicitParam(name = "pago", dataType = "Double[]", paramType = "form",
        value = "Value of the payment", required = true)
	})
	@PostMapping("/ventas/{id}/pay")
	public ResponseEntity<MyListNoPagingApiResponse<Cambio>> pay(
			@PathVariable Long id
			, @RequestParam String formaPago
			, @RequestParam List<Double> pago) {
		MyApiResponse<Venta> response = service.findById(id);
		MyListNoPagingApiResponse<Cambio> responseCambio = new MyListNoPagingApiResponse<>();
		MyApiResponse<Expendedora> responseExpendedora;
		if (response.getContent() == null) {
			responseCambio.setError(true);
			responseCambio.setMessage(response.getMessage());
			return new ResponseEntity<>(responseCambio, HttpStatus.NOT_FOUND);
		}
		if (formaPago == null || formaPago.isBlank()
				|| (!formaPago.equals("E") && !formaPago.equals("T"))) {
			responseCambio.setError(true);
			responseCambio.setMessage("Invalid pay method, it could be E or T.");
			return new ResponseEntity<>(responseCambio, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		Venta v = response.getContent();
		if (v.isPagada()) {
			responseCambio.setError(true);
			responseCambio.setMessage("The sale is already paid.");
			return new ResponseEntity<>(responseCambio, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		if (!v.getFormaPago().contains(formaPago)) {
			responseCambio.setError(true);
			responseCambio.setMessage("Invalid pay method, for the sale.");
			return new ResponseEntity<>(responseCambio, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		Double pagoTotal = 0.0;
		if (formaPago.equals("T")) {
			// TODO Invocar pago con tarjeta
			pagoTotal = v.getTotal();
		} else {
			for (Double din : pago) {
				if (!billetes.contains(din) && !monedas.contains(din)) {
					responseCambio.setError(true);
					responseCambio.setMessage("You inserted an invalid bill or coin: " + din);
					return new ResponseEntity<>(responseCambio, HttpStatus.INTERNAL_SERVER_ERROR);
				}
				pagoTotal += din;
			}
			if (pagoTotal < v.getTotal()) {
				responseCambio.setError(true);
				responseCambio.setMessage("Your pay should be equal or greater than the sale");
				return new ResponseEntity<>(responseCambio, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		int[] vuelto = Util.vuelto(Util.round(pagoTotal - v.getTotal(), 2)
				, monedas.toArray(new Double[0]));
		log.info("Pago: "+pagoTotal+" Monto: "+v.getTotal()+" Vuelto " + Arrays.toString(vuelto));
		List<Cambio> cambio = new ArrayList<>();
		for (int i = 0; i < vuelto.length; i++) {
			cambio.add(new Cambio(String.valueOf(monedas.get(i)), vuelto[i]));
		}
		responseCambio.setError(false);
		responseCambio.setMessage("Payment was successful.");
		responseCambio.setContent(cambio);
		v.setPagada(true);
		response = service.save(v);
		if (response.isError()) {
			responseCambio.setError(true);
			responseCambio.setMessage(response.getMessage());
			return new ResponseEntity<>(responseCambio, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (formaPago.equals("E")) {
			responseExpendedora = expendedoraService.findById(v.getExpendedora().getId());
			if (responseExpendedora.getContent().getDineroDisponible() >= montoRetiro) {
				responseExpendedora.getContent().setRecolectarDinero(true);
				responseExpendedora = expendedoraService.save(responseExpendedora.getContent());
				if (responseExpendedora.isError()) {
					v.setPagada(false);
					response = service.save(v);
					responseCambio.setContent(null);
					responseCambio.setError(true);
					responseCambio.setMessage(responseExpendedora.getMessage());
					return new ResponseEntity<>(responseCambio, HttpStatus.INTERNAL_SERVER_ERROR);	
				}
			}
		}
		return new ResponseEntity<>(responseCambio, HttpStatus.OK);
	}

	private Map<String, String> validateVenta(Venta venta) {
		Map<String, String> errors = new HashMap<>();
		if (venta.getFormaPago() == null || venta.getFormaPago().isEmpty()) {
			errors.put("formaPago", "Pay method cannot be empty");
		}
		if (venta.getExpendedora() == null) {
			errors.put("expendedora", "Machine cannot be empty");
		}	
		if (venta.getItems() == null || venta.getItems().isEmpty()) {
			errors.put("items", "Items cannot be empty");
		} else {
			for (VentaItem vi : venta.getItems()) {
				if (vi.getItem().getId() == null) {
					errors.put("items.item.id", "Item ID cannot be empty");
					break;
				}
				if (vi.getCantidad() == null || vi.getCantidad() <= 0) {
					errors.put("items.cantidad", "Item Cantidad should be greater than 0");
					break;
				}
			}
		}
		return errors;
	}
}
