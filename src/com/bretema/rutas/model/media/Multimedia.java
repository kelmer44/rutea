package com.bretema.rutas.model.media;

import com.bretema.rutas.R;
import com.bretema.rutas.enums.MMType;
import com.bretema.rutas.model.poi.Poi;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Define archivos multimedia, tanto imágenes como vídeos, asociados a la rutas
 * @author Gabriel Sanmartín Díaz
 *
 */
@DatabaseTable
public class Multimedia {
	/**
	 * Identificador único
	 */
	@DatabaseField(generatedId = true)
	private Integer id;
	
	/**
	 * URI dentro de la carpeta assets (solución temporal
	 * TODO: fix this shit
	 */
	@DatabaseField
	private String uri;
	
	/**
	 * URI al thumb
	 */
	@DatabaseField
	private String thumbUri;
	
	/**
	 * Nombre del recurso multimedia
	 */
	@DatabaseField
	private String nombre;
	
	/**
	 * Pequeña descripción de la foto o vídeo
	 */
	@DatabaseField
	private String descripcion;
	
	/**
	 * Tipo de recurso según el enum MMtype (Imagen, Vídeo, etc.)
	 */
	@DatabaseField(dataType = DataType.ENUM_STRING)
	private MMType tipo;
	
	/**
	 * POI asociado (a través de él deberíamos acceder a la ruta)
	 */
	@DatabaseField(foreign = true, columnName = "poiId")
	private Poi poi;
	
	/**
	 * Orden de muestreo (en caso de que éste sea necesario)
	 */
	@DatabaseField
	private int orden;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public MMType getTipo() {
		return tipo;
	}

	public void setTipo(MMType tipo) {
		this.tipo = tipo;
	}

	public Poi getPoi() {
		return poi;
	}

	public void setPoi(Poi poi) {
		this.poi = poi;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}
	
	public Multimedia(){
		super();
	}

	public Multimedia(String uri, String nombre, String descripcion, MMType tipo, Poi poi, int orden) {
		super();
		this.uri = uri;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.tipo = tipo;
		this.poi = poi;
		this.orden = orden;
	}

	public String getThumbUri() {
		return thumbUri;
	}

	public void setThumbUri(String thumbUri) {
		this.thumbUri = thumbUri;
	}
	
	
	
}
