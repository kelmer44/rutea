package com.bretema.rutas.model.poi.dao.impl;

import android.content.Context;

import com.bretema.rutas.R;
import com.bretema.rutas.core.dao.impl.GenericDaoOrmLiteImpl;
import com.bretema.rutas.model.poi.Poi;
import com.bretema.rutas.model.poi.dao.PoiDao;

public class PoiDaoImpl extends GenericDaoOrmLiteImpl<Poi, Integer> implements PoiDao {

	public PoiDaoImpl(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	
}
