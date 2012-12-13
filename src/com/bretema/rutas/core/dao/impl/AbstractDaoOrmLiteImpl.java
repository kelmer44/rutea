package com.bretema.rutas.core.dao.impl;

import android.content.Context;

/**
 * Clase abstracta que centraliza la definición de un DAO de OrmLite
 * @author Gabriel Sanmartín Díaz
 *
 */
public class AbstractDaoOrmLiteImpl {

	/**
	 * Application context
	 * se pasa al instanciar cada dao
	 */
    private Context context;

    public Context getContext() {
        return context;
    }

    protected void setContext(Context context) {
        this.context = context;
    }
    
    
    
}
