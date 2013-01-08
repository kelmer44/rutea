package com.bretema.rutas.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.j256.ormlite.stmt.QueryBuilder;

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
	
	public List<E> findAll(String orderBy, boolean isOrderAsc);
	
	public List<E> findAll(String orderBy, boolean isOrderAsc, long firstResult, long maxResults);
	
	public List<E> findByProperty(String propertyName, Object propertyValue);
	
	public List<E> findByProperty(String propertyName, Object propertyValue, String orderBy, boolean isOrderAsc);
	
	public List<E> findByProperty(String propertyName, Object propertyValue, String orderBy, boolean isOrderAsc, long firstResult, long maxResults);
	
	public List<E> findByProperties(Map<String, Object> values);
	
	public List<E> findByProperties(Map<String, Object> values, String orderBy, boolean isOrderAsc);
	
	public List<E> findByProperties(Map<String, Object> values, String orderBy, boolean isOrderAsc, long firstResult, long maxResults);
	
	public Long count();

	public void deleteAll();
	
	
}