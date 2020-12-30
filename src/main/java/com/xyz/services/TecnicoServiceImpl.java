package com.xyz.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.models.dao.TecnicoDao;
import com.xyz.models.entity.Tecnico;
import com.xyz.responses.MyApiResponse;
import com.xyz.responses.MyListApiResponse;

@Service
public class TecnicoServiceImpl implements ITecnicoService {

	@Autowired
	private TecnicoDao dao;
	
	private Logger log = LoggerFactory.getLogger(TecnicoServiceImpl.class);

	@Override
	public MyListApiResponse<Tecnico> findAll(int pagina, int cantidad, String orderBy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyListApiResponse<Tecnico> findByNombreIgnoreCaseContaining(String nombre, int pagina, int cantidad,
			String orderBy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyApiResponse<Tecnico> findById(Long id) {
		Tecnico i = dao.findById(id).orElse(null);
		MyApiResponse<Tecnico> response = new MyApiResponse<>();
		response.setContent(i);
		response.setMessage(null);
		response.setError(false);;
		if (i == null) {
			response.setMessage("Tech " + id + " not found");
			response.setError(true);
		}
		return response;
	}

	@Override
	public MyApiResponse<Tecnico> save(Tecnico tecnico) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
	}

}
