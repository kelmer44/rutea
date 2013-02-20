package com.bretema.rutas.model.mapimages.dao.impl;

import android.content.Context;

import com.bretema.rutas.core.dao.impl.GenericDaoOrmLiteImpl;
import com.bretema.rutas.model.mapimages.MapImage;
import com.bretema.rutas.model.mapimages.dao.MapImageDao;

public class MapImageDaoImpl extends GenericDaoOrmLiteImpl<MapImage, Integer> implements MapImageDao {
	
	public MapImageDaoImpl(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
}
