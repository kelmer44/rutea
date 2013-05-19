package com.bretema.rutas.model.codigo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable
public class Codigo {

	@DatabaseField(generatedId = true)
	private Integer id;
	
	@DatabaseField(canBeNull=false, unique=true)
	private String fullCode;
	
	@DatabaseField(canBeNull=false)
	private Date activationDate;
	
	@DatabaseField(canBeNull=false)
	private Date deactivationDate;
	
	@DatabaseField
	private boolean valid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFullCode() {
		return fullCode;
	}

	public void setFullCode(String fullCode) {
		this.fullCode = fullCode;
	}

	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	public Date getDeactivationDate() {
		return deactivationDate;
	}

	public void setDeactivationDate(Date deactivationDate) {
		this.deactivationDate = deactivationDate;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	
	
}
