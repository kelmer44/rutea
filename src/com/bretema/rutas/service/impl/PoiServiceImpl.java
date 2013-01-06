package com.bretema.rutas.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;

import com.bretema.rutas.R;
import com.bretema.rutas.enums.PoiType;
import com.bretema.rutas.model.poi.Poi;
import com.bretema.rutas.model.poi.dao.PoiDao;
import com.bretema.rutas.model.poi.dao.impl.PoiDaoImpl;
import com.bretema.rutas.model.ruta.dao.impl.RutaDaoImpl;
import com.bretema.rutas.service.PoiService;

public class PoiServiceImpl implements PoiService {
	private static final String LOG_TAG = PoiServiceImpl.class.getSimpleName();

	private PoiDao poiDao;
	
	private Context context;
	
	/**
	 * En lugar de inyectar tenemos que hacerlo a mano, al no tener spring
	 * Existe la opción de usar RoboGuice pero paso
	 * @param context
	 */
	public PoiServiceImpl(Context context){
		this.context = context;
		poiDao = new PoiDaoImpl(context);
	}
	
	@Override
	public List<Poi> findAll() {
		return poiDao.findAll();
	}

	@Override
	public Poi getPoi(Integer id) {
		return poiDao.findById(id);
	}

	@Override
	public List<Poi> getPoiByRuta(Integer rutaId) {
		return poiDao.findByProperty("rutaId", rutaId);
	}

	@Override
	public List<Poi> getSimplePoiOrderedByRuta(Integer rutaId) {
		HashMap<String, Object> filterValues = new HashMap<String, Object>();
		filterValues.put("rutaId", rutaId);
		filterValues.put("tipo", PoiType.SimplePoi);
		return poiDao.findByProperties(filterValues,"orden", true);
	}

	@Override
	public List<Poi> getOtherPoiOrderedByRuta(Integer rutaId) {
		//TODO IMPROVE
		List<Poi> listaTodos = new ArrayList<Poi>();
		for(Poi p: getPoiByRuta(rutaId)){
			if(p.getTipo() != PoiType.SimplePoi && p.getTipo() != PoiType.SecondaryPoi)
				listaTodos.add(p);
		}
		return listaTodos;
	}
	
	@Override
	public List<Poi> getAllSimplePoi(){
		return poiDao.findByProperty("tipo", PoiType.SimplePoi);
	}

}
