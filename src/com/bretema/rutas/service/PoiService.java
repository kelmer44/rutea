package com.bretema.rutas.service;

import java.util.List;

import com.bretema.rutas.R;
import com.bretema.rutas.model.poi.Poi;

/**
 * Servicio de obtenci�n de punto de inter�s, tanto normales (tur�sticos)
 * como funcionales (Farmacias, restaurantes, etc...)
 * @author Gabriel Sanmart�n D�az
 *
 */
public interface PoiService {

	
	public List<Poi> findAll();
	
	public Poi getPoi(Integer id);
	
	public List<Poi> getPoiByRuta(Integer rutaId);
	
	public List<Poi> getSimplePoiOrderedByRuta(Integer rutaId);
	
}
