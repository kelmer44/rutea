package com.bretema.rutas.service.impl;

import java.util.List;

import android.content.Context;

import com.bretema.rutas.model.ruta.Ruta;
import com.bretema.rutas.model.ruta.dao.RutaDao;
import com.bretema.rutas.model.ruta.dao.impl.RutaDaoImpl;
import com.bretema.rutas.service.RutaService;


public class RutaServiceImpl implements RutaService {
	private static final String LOG_TAG = RutaServiceImpl.class.getSimpleName();

	
	private RutaDao rutaDao;
	
	private Context context;
	
	/**
	 * En lugar de inyectar tenemos que hacerlo a mano, al no tener spring
	 * Existe la opción de usar RoboGuice pero paso
	 * @param context
	 */
	public RutaServiceImpl(Context context){
		this.context = context;
		rutaDao = new RutaDaoImpl(context);
	}
		
	@Override
	public List<Ruta> findAll() {
		return rutaDao.findAll();
	}

	@Override
	public Ruta getRuta(Integer id) {
		return rutaDao.findById(id);
	}

	@Override
	public Ruta save(Ruta ruta) {
		return rutaDao.save(ruta);
	}

	
}
