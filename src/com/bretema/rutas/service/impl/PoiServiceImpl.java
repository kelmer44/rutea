package com.bretema.rutas.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;

import com.bretema.rutas.enums.PoiType;
import com.bretema.rutas.model.poi.Poi;
import com.bretema.rutas.model.poi.dao.PoiDao;
import com.bretema.rutas.model.poi.dao.impl.PoiDaoImpl;
import com.bretema.rutas.model.poiruta.PoiRuta;
import com.bretema.rutas.model.poiruta.dao.PoiRutaDao;
import com.bretema.rutas.model.poiruta.dao.impl.PoiRutaDaoImpl;
import com.bretema.rutas.model.ruta.Ruta;
import com.bretema.rutas.service.PoiService;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;

public class PoiServiceImpl implements PoiService {
	private static final String	LOG_TAG	= PoiServiceImpl.class.getSimpleName();

	private PoiDao				poiDao;
	private PoiRutaDao			poiRutaDao;
	private Context				context;

	/**
	 * En lugar de inyectar tenemos que hacerlo a mano, al no tener spring
	 * Existe la opción de usar RoboGuice pero paso
	 * 
	 * @param context
	 */
	public PoiServiceImpl(Context context) {
		this.context = context;
		poiDao = new PoiDaoImpl(context);
		poiRutaDao = new PoiRutaDaoImpl(context);
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
		
		List<PoiRuta> poiRutas = poiRutaDao.findByProperty(PoiRuta.RUTA_ID_FIELD_NAME, rutaId);
		List<Poi> pois = new ArrayList<Poi>();
		
		for(PoiRuta pr: poiRutas){
			pois.add(pr.getPoi());
		}
	
		return pois;
	}

	@Override
	public List<Poi> getSimplePoiOrderedByRuta(Integer rutaId) {
		HashMap<String, Object> filterValues = new HashMap<String, Object>();
		filterValues.put("rutaId", rutaId);
		filterValues.put("tipo", PoiType.SimplePoi);
		return poiDao.findByProperties(filterValues, "orden", true);
	}

	@Override
	public List<Poi> getOtherPoiOrderedByRuta(Integer rutaId) {
		// TODO IMPROVE
		List<Poi> listaTodos = new ArrayList<Poi>();
		for (Poi p : getPoiByRuta(rutaId)) {
			if (p.getTipo() != PoiType.SimplePoi && p.getTipo() != PoiType.SecondaryPoi)
				listaTodos.add(p);
		}
		return listaTodos;
	}

	@Override
	public List<Poi> getAllTouristPois() {
		return poiDao.getAllTouristPoints();		
	}

	/*private PreparedQuery<Poi> makePoiForRutaQuery() throws java.sql.SQLException {
		// build our inner query for UserPost objects
		QueryBuilder<PoiRuta, Integer> poiRutaQb = poiRutaDao.getQueryBuilder();
		// just select the post-id field
		poiRutaQb.selectColumns(PoiRuta.POI_ID_FIELD_NAME);
		SelectArg userSelectArg = new SelectArg();
		// you could also just pass in user1 here
		poiRutaQb.where().eq(PoiRuta.RUTA_ID_FIELD_NAME, userSelectArg);

		// build our outer query for Post objects
		QueryBuilder<Poi, Integer> postQb = poiDao.getQueryBuilder();
		// where the id matches in the post-id from the inner query
		postQb.where().in("id", poiRutaQb);
		return postQb.prepare();
	}*/
}
