package com.bretema.rutas.model.codigo.dao.impl;

import android.content.Context;

import com.bretema.rutas.core.dao.impl.GenericDaoOrmLiteImpl;
import com.bretema.rutas.model.codigo.Codigo;
import com.bretema.rutas.model.codigo.dao.CodigoDao;
import com.bretema.rutas.model.ruta.Ruta;
import com.bretema.rutas.model.ruta.dao.RutaDao;
import com.bretema.rutas.model.ruta.dao.impl.RutaDaoImpl;

public class CodigoDaoImpl extends GenericDaoOrmLiteImpl<Codigo, Integer> implements CodigoDao{

	private static final String LOG_TAG = CodigoDaoImpl.class.getSimpleName();
	
	public CodigoDaoImpl(Context context) {
		super(context);
	}

}
