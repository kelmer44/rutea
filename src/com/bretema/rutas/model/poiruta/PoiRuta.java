package com.bretema.rutas.model.poiruta;

import com.bretema.rutas.model.poi.Poi;

import com.bretema.rutas.model.ruta.Ruta;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 
 * Tabla join que une rutas con pois (porque los pois pueden repetirse entre
 * rutas)
 * 
 * 
 * 
 * @author kelmer
 * 
 */
@DatabaseTable
public class PoiRuta {

	public final static String	RUTA_ID_FIELD_NAME	= "id_ruta";
	public final static String	POI_ID_FIELD_NAME	= "id_poi";

	@DatabaseField(generatedId = true)
	int							id;

	@DatabaseField(foreign = true, columnName = RUTA_ID_FIELD_NAME)
	Ruta						ruta;

	@DatabaseField(foreign = true, columnName = POI_ID_FIELD_NAME)
	Poi							poi;

	@DatabaseField
	int							orden;

	public Ruta getRuta() {
		return ruta;
	}

	public void setRuta(Ruta ruta) {
		this.ruta = ruta;
	}

	public Poi getPoi() {
		return poi;
	}

	public void setPoi(Poi poi) {
		this.poi = poi;
	}

	PoiRuta() {
	}

	public PoiRuta(Ruta ruta, Poi poi) {
		this.ruta = ruta;
		this.poi = poi;
	}
}
