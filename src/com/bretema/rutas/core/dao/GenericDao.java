package com.bretema.rutas.core.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Gabriel Sanmartín Díaz
 *
 * @param <T>
 * @param <ID>
 */
public interface GenericDao<E, PK extends Serializable> {

    public E save(E entity);

    public boolean exists(PK id);

    public E update(E entity);

    public void delete(E entity);

    public E findById(PK id);

    public List<E> findAll();

    public Long count();
    
    public void deleteAll();
}