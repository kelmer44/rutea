package com.bretema.rutas.model.poiruta.dao.impl;

import android.content.Context;

import com.bretema.rutas.core.dao.impl.GenericDaoOrmLiteImpl;
import com.bretema.rutas.model.poiruta.PoiRuta;
import com.bretema.rutas.model.poiruta.dao.PoiRutaDao;

public class PoiRutaDaoImpl extends GenericDaoOrmLiteImpl<PoiRuta, Integer> implements PoiRutaDao {

	public PoiRutaDaoImpl(Context context) {
		super(context);
	}

	
	 
}
