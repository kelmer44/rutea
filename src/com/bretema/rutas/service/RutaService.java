package com.bretema.rutas.service;

import java.util.List;

import com.bretema.rutas.model.ruta.Ruta;

public interface RutaService {

	
    /**
     * Recupera todas las rutas registradas.
     * @return Todas las rutas.
     */
    public List<Ruta> findAll();
    
    /**
     * REcupera una ruta dado un id
     * @param id de la ruta
     * @return ruta
     */
    public Ruta getRuta(Integer id);
    
    /**
     * Persiste una ruta en base de datos
     * @param ruta
     * @return el objeto introducido
     */
    public Ruta save(Ruta ruta);
    
    /**
     * Elimina todas las rutas almacenadas
     */
    public void deleteAllRutas();
}
