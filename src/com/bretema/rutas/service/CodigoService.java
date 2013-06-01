package com.bretema.rutas.service;

import com.bretema.rutas.model.codigo.Codigo;

import java.util.List;

public interface CodigoService {

	
	public List<Codigo> findAll();
	
	public Codigo getCodigo(Integer id);
	
	public Codigo getCodigoByCodigo(String codigo);
	
	public Codigo save(String codigo, int numMins);
	
	public boolean codeUsed(String codigo);
	
	public void resetCodes();
	
	
	
}
