package com.bretema.rutas.model.media.dao.impl;

import java.util.List;

import android.content.Context;

import com.bretema.rutas.core.dao.impl.GenericDaoOrmLiteImpl;
import com.bretema.rutas.model.media.Multimedia;
import com.bretema.rutas.model.media.dao.MultimediaDao;

public class MultimediaDaoImpl extends GenericDaoOrmLiteImpl<Multimedia, Integer> implements MultimediaDao {

	public MultimediaDaoImpl(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

}
