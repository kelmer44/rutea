package com.bretema.rutas.model.ruta.dao.impl;

import android.content.Context;

import com.bretema.rutas.R;
import com.bretema.rutas.core.dao.impl.GenericDaoOrmLiteImpl;
import com.bretema.rutas.model.ruta.Ruta;
import com.bretema.rutas.model.ruta.dao.RutaDao;

public class RutaDaoImpl extends GenericDaoOrmLiteImpl<Ruta, Integer> implements RutaDao {

	private static final String LOG_TAG = RutaDaoImpl.class.getSimpleName();
	
	
	public RutaDaoImpl(final Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}


}
