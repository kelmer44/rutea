package com.bretema.rutas.model.poi.dao;

import java.util.List;

import com.bretema.rutas.core.dao.GenericDao;
import com.bretema.rutas.model.poi.Poi;

public interface PoiDao extends GenericDao<Poi, Integer>{

	List<Poi> getAllTouristPoints();
	
}
