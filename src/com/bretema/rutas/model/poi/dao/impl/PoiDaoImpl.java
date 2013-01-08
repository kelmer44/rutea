package com.bretema.rutas.model.poi.dao.impl;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.bretema.rutas.core.dao.impl.GenericDaoOrmLiteImpl;
import com.bretema.rutas.enums.PoiType;
import com.bretema.rutas.model.poi.Poi;
import com.bretema.rutas.model.poi.dao.PoiDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

public class PoiDaoImpl extends GenericDaoOrmLiteImpl<Poi, Integer> implements PoiDao {

	public PoiDaoImpl(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Poi> getAllTouristPoints() {
		List<Poi> results = null;
		try {
			QueryBuilder<Poi, Integer> builder = dao.queryBuilder();
			Where<Poi,Integer> where = builder.where();
			where.eq("tipo", PoiType.SimplePoi);
			where.or();
			where.eq("tipo", PoiType.SecondaryPoi);
			results = builder.query();
		} catch (SQLException e) {
			throwFatalException(e);
		}
		return results;
	}

	public List<Poi> getAllTouristPointsByRuta(Integer rutaId) {
		List<Poi> results = null;
//		try {x
//			QueryBuilder<Poi, Integer> builder = dao.queryBuilder();
//			Where<Poi,Integer> where = builder.where();
//			where.eq("tipo", PoiType.SimplePoi);
//			where.or();
//			where.eq("tipo", PoiType.SecondaryPoi);
//			
//			
//			
//			results = builder.query();
//		} catch (SQLException e) {
//			throwFatalException(e);
//		}
		return results;
	}
}
