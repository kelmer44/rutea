package com.bretema.rutas.service.impl;

import java.util.List;

import android.content.Context;

import com.bretema.rutas.model.media.Multimedia;
import com.bretema.rutas.model.media.dao.MultimediaDao;
import com.bretema.rutas.model.media.dao.impl.MultimediaDaoImpl;
import com.bretema.rutas.service.MMService;

public class MMServiceImpl implements MMService {
	private static final String	LOG_TAG	= MMServiceImpl.class.getSimpleName();

	private MultimediaDao multimediaDao;
	private Context				context;
	
	/**
	 * En lugar de inyectar tenemos que hacerlo a mano, al no tener spring
	 * Existe la opción de usar RoboGuice pero paso
	 * 
	 * @param context
	 */
	public MMServiceImpl(Context context) {
		this.context = context;
		multimediaDao = new MultimediaDaoImpl(context);
	}

	
	@Override
	public List<Multimedia> findAll() {
		return multimediaDao.findAll();
	}

	@Override
	public Multimedia getMultimedia(Integer id) {
		return multimediaDao.findById(id);
	}

}
