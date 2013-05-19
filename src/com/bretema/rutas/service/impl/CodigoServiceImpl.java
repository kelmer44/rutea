package com.bretema.rutas.service.impl;

import android.content.Context;

import com.bretema.rutas.model.codigo.Codigo;
import com.bretema.rutas.model.codigo.dao.CodigoDao;
import com.bretema.rutas.model.codigo.dao.impl.CodigoDaoImpl;
import com.bretema.rutas.model.ruta.dao.impl.RutaDaoImpl;
import com.bretema.rutas.service.CodigoService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CodigoServiceImpl implements CodigoService {
	private static final String LOG_TAG = CodigoServiceImpl.class.getSimpleName();

	
	private CodigoDao codigoDao;
	
	private Context context;
	
	/**
	 * En lugar de inyectar tenemos que hacerlo a mano, al no tener spring
	 * Existe la opción de usar RoboGuice pero paso
	 * @param context
	 */
	public CodigoServiceImpl(Context context){
		this.context = context;
		codigoDao = new CodigoDaoImpl(context);
	}
	
	@Override
	public List<Codigo> findAll() {
		return codigoDao.findAll();
	}

	@Override
	public Codigo getCodigo(Integer id) {
		return codigoDao.findById(id);
	}
	

	@Override
	public Codigo save(String serial) {
		// TODO Auto-generated method stub
		Codigo codigo = new Codigo();
		codigo.setFullCode(serial.trim());
		codigo.setValid(true); 
		codigo.setActivationDate(new Date());
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_YEAR, 1);
		codigo.setDeactivationDate(cal.getTime());
		codigo.setValid(true);
		return codigoDao.save(codigo);
	}

	@Override
	public boolean codeUsed(String codigo) {
		List<Codigo> codigos = codigoDao.findByProperty("fullCode", codigo);
		if(codigos==null || codigos.size()==0)
			return false;
		else
			return true;
	}

    @Override
    public Codigo getCodigoByCodigo(String codigo) {
        
        List<Codigo> lista = codigoDao.findByProperty("fullCode", codigo);
        
        if(lista.size()>0)
            return lista.get(0);
        else
            return null;
    }

}
