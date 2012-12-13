package com.bretema.rutas.model.poi;

import com.bretema.rutas.enums.PoiType;
import com.bretema.rutas.model.ruta.Ruta;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Define un punto de interés en el mapa
 * 
 * @author Gabriel Sanmartín Díaz
 *
 */
@DatabaseTable
public class Poi {
	
	@DatabaseField(generatedId = true)
	private Integer id;
	
	@DatabaseField
	private String nombre;
	
	@DatabaseField
	private String descripcion;
	
	@DatabaseField(dataType = DataType.ENUM_STRING)
	private PoiType tipo;
	
	@DatabaseField(foreign = true, columnName = "rutaId")
	private Ruta ruta;
	
	@DatabaseField
	private double latitude;
	
	@DatabaseField
	private double longitude;
	
	public Poi() {
		super();
	}

	public Poi(Integer id, String nombre, String descripcion, PoiType tipo, Ruta ruta, double latitude, double longitude) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.tipo = tipo;
		this.ruta = ruta;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PoiType getTipo() {
		return tipo;
	}

	public void setTipo(PoiType tipo) {
		this.tipo = tipo;
	}

	public Ruta getRuta() {
		return ruta;
	}

	public void setRuta(Ruta ruta) {
		this.ruta = ruta;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	
	
	
}
