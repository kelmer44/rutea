package com.bretema.rutas.service;

import java.util.List;

import com.bretema.rutas.R;
import com.bretema.rutas.model.poi.Poi;

/**
 * Servicio de obtención de punto de interés, tanto normales (turísticos)
 * como funcionales (Farmacias, restaurantes, etc...)
 * @author Gabriel Sanmartín Díaz
 *
 */
public interface PoiService {

	
	public List<Poi> findAll();
	
	public Poi getPoi(Integer id);
	
	public List<Poi> getPoiByRuta(Integer rutaId);
	
	public List<Poi> getSimplePoiOrderedByRuta(Integer rutaId);
	
}
