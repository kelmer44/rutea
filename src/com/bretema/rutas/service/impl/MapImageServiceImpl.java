package com.bretema.rutas.service.impl;

import java.util.List;

import android.content.Context;

import com.bretema.rutas.model.mapimages.MapImage;
import com.bretema.rutas.model.mapimages.dao.MapImageDao;
import com.bretema.rutas.model.mapimages.dao.impl.MapImageDaoImpl;
import com.bretema.rutas.model.poi.Poi;
import com.bretema.rutas.service.MapImageService;

public class MapImageServiceImpl implements MapImageService {
	private static final String	LOG_TAG	= MapImageServiceImpl.class.getSimpleName();

	private MapImageDao			mapImageDao;
	private Context				context;

	/**
	 * En lugar de inyectar tenemos que hacerlo a mano, al no tener spring
	 * Existe la opción de usar RoboGuice pero paso
	 * 
	 * @param context
	 */
	public MapImageServiceImpl(Context context) {
		this.context = context;
		mapImageDao = new MapImageDaoImpl(context);
	}

	@Override
	public List<MapImage> findAll() {
		return mapImageDao.findAll();
	}

	@Override
	public MapImage getMapImage(Integer id) {
		return mapImageDao.findById(id);
	}

	@Override
	public List<MapImage> getMapImagesByMap(String mapName) {
		return mapImageDao.findByProperty("mapa", mapName);
	}


}
