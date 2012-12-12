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
public class Ruta implements Serializable{

	@DatabaseField(generatedId = true)
	private Integer id;
	
	@DatabaseField
	private String nombre;
	
	@DatabaseField
	private String description;

	
	
	
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
	
	
	
}
