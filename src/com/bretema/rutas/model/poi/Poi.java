package com.bretema.rutas.model.poi;

import com.bretema.rutas.R;
import com.bretema.rutas.enums.PoiType;
import com.bretema.rutas.model.media.Multimedia;
import com.bretema.rutas.model.ruta.Ruta;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Define un punto de interés en el mapa.
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
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = "rutaId")
	private Ruta ruta;
	
	@DatabaseField
	private double latitude;
	
	@DatabaseField
	private double longitude;
	
	@DatabaseField
	private int orden;
	
	@ForeignCollectionField(eager = true)
    ForeignCollection<Multimedia> media;
	
	public Poi() {
		super();
	}

	public Poi(final Integer id, final String nombre, final String descripcion, final PoiType tipo, final Ruta ruta, final double latitude, final double longitude) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.tipo = tipo;
		this.ruta = ruta;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public final Integer getId() {
		return id;
	}

	public final void setId(final Integer id) {
		this.id = id;
	}

	public final PoiType getTipo() {
		return tipo;
	}

	public final void setTipo(final PoiType tipo) {
		this.tipo = tipo;
	}

	public final Ruta getRuta() {
		return ruta;
	}

	public final void setRuta(final Ruta ruta) {
		this.ruta = ruta;
	}

	public final double getLatitude() {
		return latitude;
	}

	public final void setLatitude(final double latitude) {
		this.latitude = latitude;
	}

	public final double getLongitude() {
		return longitude;
	}

	public final void setLongitude(final double longitude) {
		this.longitude = longitude;
	}
	
	

	
	public final String getNombre() {
		return nombre;
	}

	public final void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	public final String getDescripcion() {
		return descripcion;
	}

	public final void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	public final int getOrden() {
		return orden;
	}

	public final void setOrden(final int orden) {
		this.orden = orden;
	}

	public ForeignCollection<Multimedia> getMedia() {
		return media;
	}

	public void setMedia(ForeignCollection<Multimedia> media) {
		this.media = media;
	}
	
}
