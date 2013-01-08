package com.bretema.rutas.model.ruta;

import java.io.Serializable;

import com.bretema.rutas.R;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Define una instancia de rutas
 * 
 * @author Gabriel Sanmartín Díaz
 * 
 */

@DatabaseTable
public class Ruta {

	@DatabaseField(generatedId = true)
	private Integer	id;

	/**
	 * Nombre de la ruta
	 */
	@DatabaseField
	private String	nombre;

	/**
	 * Descripción de la ruta (introducción)
	 */
	@DatabaseField
	private String	description;

	/**
	 * Imagen principal
	 */
	@DatabaseField
	private String	mainImagePath;

	/**
	 * Descripción corta (en desuso?)
	 */
	@DatabaseField
	private String	shortDescription;

	/**
	 * URI del archivo GPX
	 */
	@DatabaseField
	private String	routeFile;

	/**
	 * imagen de muestreo en la pantalla principal
	 */
	@DatabaseField
	private String	listImagePath;

	/**
	 * Archivo de audio con la introducción de la ruta
	 */
	@DatabaseField
	private String	introAudioPath;

	/**
	 * Duracion temporal de la ruta
	 */
	@DatabaseField
	private String	duracion;

	@DatabaseField
	private String balloonImagePath;
	/**
	 * km de longitud de la ruta
	 */
	@DatabaseField
	private float	km;

	public Ruta() {
		super();
	}

	public Ruta(String nombre, String description, String mainImagePath) {
		super();
		this.nombre = nombre;
		this.description = description;
		this.mainImagePath = mainImagePath;
	}

	public String getListImagePath() {
		return listImagePath;
	}

	public void setListImagePath(String listImagePath) {
		this.listImagePath = listImagePath;
	}

	public String getIntroAudioPath() {
		return introAudioPath;
	}

	public void setIntroAudioPath(String introAudioPath) {
		this.introAudioPath = introAudioPath;
	}

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	public float getKm() {
		return km;
	}

	public void setKm(float km) {
		this.km = km;
	}

	public String getMainImagePath() {
		return mainImagePath;
	}

	public void setMainImagePath(String mainImagePath) {
		this.mainImagePath = mainImagePath;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getRouteFile() {
		return routeFile;
	}

	public void setRouteFile(String routeFile) {
		this.routeFile = routeFile;
	}

	public Ruta(String nombre, String description, String mainImagePath, String shortDescription, String routeFile, String listImagePath, String introAudioPath, String duracion, float km) {
		super();
		this.nombre = nombre;
		this.description = description;
		this.mainImagePath = mainImagePath;
		this.shortDescription = shortDescription;
		this.routeFile = routeFile;
		this.listImagePath = listImagePath;
		this.introAudioPath = introAudioPath;
		this.duracion = duracion;
		this.km = km;
	}

}
