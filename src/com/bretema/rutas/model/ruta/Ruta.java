package com.bretema.rutas.model.ruta;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Define una instancia de rutas
 * @author Gabriel Sanmartín Díaz
 *
 */

@DatabaseTable
public class Ruta {

	@DatabaseField(generatedId = true)
	private Integer id;
	
	@DatabaseField
	private String nombre;
	
	@DatabaseField
	private String description;

	@DatabaseField
	private String mainImagePath;
	
	@DatabaseField
	private String shortDescription;
	
	@DatabaseField
	private String routeFile;

	

	public Ruta() {
		super();
	}

	public Ruta(String nombre, String description, String mainImagePath) {
		super();
		this.nombre = nombre;
		this.description = description;
		this.mainImagePath = mainImagePath;
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
	
}
