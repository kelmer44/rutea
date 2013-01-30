package com.bretema.rutas.service;

import java.util.List;

import com.bretema.rutas.model.media.Multimedia;

public interface MMService {

	public List<Multimedia> findAll();

	public Multimedia getMultimedia(Integer id);

}
