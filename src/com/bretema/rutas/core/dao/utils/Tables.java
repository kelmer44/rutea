package com.bretema.rutas.core.dao.utils;

import com.bretema.rutas.R;
import com.bretema.rutas.model.media.Multimedia;
import com.bretema.rutas.model.poi.Poi;
import com.bretema.rutas.model.ruta.Ruta;


/**
 * User: DIRK VRANCKAERT
 * Date: 05/02/11
 * Time: 16:35
 */
public enum Tables {
    RUTA(Ruta.class),
    POI(Poi.class),
    MULTIMEDIA(Multimedia.class)
    ;

    Tables(Class tableClass) {
        this.tableClass = tableClass;
    }

    private Class tableClass;

    public Class getTableClass() {
        return tableClass;
    }

    public void setTableClass(Class tableClass) {
        this.tableClass = tableClass;
    }
}