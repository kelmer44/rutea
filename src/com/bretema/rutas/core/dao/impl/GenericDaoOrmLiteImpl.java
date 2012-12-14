package com.bretema.rutas.core.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.bretema.rutas.core.dao.GenericDao;
import com.bretema.rutas.core.dao.utils.DatabaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

/**
 * generic DAO implementation to retrieve a DAO object and retrieving certain
 * data from the DB. In order to extend from this class you should implement a
 * default constructor which calls the generic dao's constructor
 * <b>GenericDaoImpl(java.lang.Class<T> clazz)</b>
 * 
 * @author Dirk Vranckaert
 */
public abstract class GenericDaoOrmLiteImpl<E, PK extends Serializable> extends
		AbstractDaoOrmLiteImpl implements GenericDao<E, PK> {
	/**
	 * Logging
	 */
	private static final String	LOG_TAG	= GenericDaoOrmLiteImpl.class
												.getSimpleName();
	/**
	 * Clase asociada al DAO
	 */
	private Class<E>			entityClass;

	/**
	 * The doa to access all of your entities.
	 */
	public Dao<E, PK>			dao;

	/**
	 * This constructor should always be called in order to have a DAO!
	 * 
	 * @param clazz
	 *            The entity-class for which the DAO should be created!
	 */
	public GenericDaoOrmLiteImpl(final Context context) {
		this.entityClass = (Class<E>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];

		Log.d(LOG_TAG, "Creating DAO for " + entityClass.getSimpleName()
				+ " from " + getClass().getSimpleName());

		OrmLiteSqliteOpenHelper helper = OpenHelperManager.getHelper(context,
				DatabaseHelper.class);
		try {
			dao = helper.getDao(entityClass);
		} catch (SQLException e) {
			throw new RuntimeException("Could not instantiate a DAO for class "
					+ entityClass.getName(), e);
		}
		// helper.close();
		// OpenHelperManager.releaseHelper();
		this.setContext(context);
	}

	/**
	 * Handles the throwing of fatal exceptions during basic SQL commands.
	 * 
	 * @param e
	 *            The exception.
	 */
	protected final void throwFatalException(final SQLException e) {
		String message = "An unknown SQL exception occured while executing a basic SQL command!";
		Log.e(LOG_TAG, message, e);
		throw new RuntimeException(message, e);
	}

	/**
	 * Get the database helper to give you low-level database access!
	 * 
	 * @return The project-custom database helper ({@link DatabaseHelper}).
	 */
	protected final DatabaseHelper<E, PK> getDatabaseHelper() {
		DatabaseHelper<E, PK> databaseHelper = (DatabaseHelper<E, PK>) OpenHelperManager
				.getHelper(getContext(), DatabaseHelper.class);
		return databaseHelper;
	}

	/**
	 * @Override
	 */
	@Override
	public final E findById(final PK id) {
		E result = null;
		try {
			result = dao.queryForId(id);
		} catch (SQLException e) {
			throwFatalException(e);
		}
		return result;
	}

	/**
	 * @Override
	 */
	@Override
	public final boolean exists(final PK id) {
		try {
			return dao.idExists(id);
		} catch (SQLException e) {
			throwFatalException(e);
		}
		return false;
	}

	/**
	 * @Override
	 */
	@Override
	public final List<E> findAll() {
		List<E> results = null;
		try {
			results = dao.queryForAll();
		} catch (SQLException e) {
			throwFatalException(e);
		}
		return results;
	}

	/**
	 * @Override
	 */
	@Override
	public final E save(final E entity) {
		try {
			dao.create(entity);
		} catch (SQLException e) {
			throwFatalException(e);
		}
		return entity;
	}

	/**
	 * @Override
	 */
	@Override
	public final E update(final E entity) {
		try {
			dao.update(entity);
		} catch (SQLException e) {
			throwFatalException(e);
		}
		return entity;
	}

	/**
	 * @Override
	 */
	@Override
	public final void delete(final E entity) {
		int result = 0;
		try {
			result = dao.delete(entity);
		} catch (SQLException e) {
			throwFatalException(e);
		}
		Log.d(LOG_TAG, result + " records are deleted!");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Long count() {
		Long result = 0L;
		try {
			result = dao.countOf();
		} catch (SQLException e) {
			throwFatalException(e);
		}
		return result;
	}

	@Override
	public final void deleteAll() {
		DeleteBuilder<E, PK> deleteBuilder = dao.deleteBuilder();
		try {
			dao.delete(deleteBuilder.prepare());
		} catch (SQLException e) {
			throwFatalException(e);
		}
	}

	@Override
	public final List<E> findByProperty(final String propertyName, final Object propertyValue) {
		List<E> results = null;
		try {
			results = dao.queryForEq(propertyName, propertyValue);
		} catch (SQLException e) {
			throwFatalException(e);
		}
		return results;
	}

	@Override
	public final List<E> findByProperties(final Map<String, Object> values) {
		List<E> results = null;
		try {
			results = dao.queryForFieldValues(values);
		} catch (SQLException e) {
			throwFatalException(e);
		}

		return results;
	}

	@Override
	public final List<E> findAll(final String orderBy, final boolean isOrderAsc) {
		List<E> results = null;
		try {
			QueryBuilder<E, PK> builder = dao.queryBuilder();
			builder.orderBy(orderBy, isOrderAsc);
			results = builder.query();
		} catch (SQLException e) {
			throwFatalException(e);
		}
		return results;
	}

	@Override
	public final List<E> findAll(final String orderBy, final boolean isOrderAsc, final long firstResult, final long maxResults) {
		List<E> results = null;
		try {
			QueryBuilder<E, PK> builder = dao.queryBuilder();
			builder.orderBy(orderBy, isOrderAsc);
			builder.offset(firstResult);
			builder.limit(maxResults);
			results = builder.query();
		} catch (SQLException e) {
			throwFatalException(e);
		}
		return results;
	}

	@Override
	public final List<E> findByProperty(final String propertyName, final Object propertyValue, final String orderBy, final boolean isOrderAsc) {
		List<E> results = null;
		try {
			QueryBuilder<E, PK> builder = dao.queryBuilder();
			builder.where().eq(propertyName, propertyValue);
			builder.orderBy(orderBy, isOrderAsc);
			results = builder.query();
		} catch (SQLException e) {
			throwFatalException(e);
		}
		return results;
	}

	@Override
	public final List<E> findByProperty(final String propertyName, final Object propertyValue, final String orderBy, final boolean isOrderAsc, final long firstResult, final long maxResults) {
		List<E> results = null;
		try {
			QueryBuilder<E, PK> builder = dao.queryBuilder();
			builder.where().eq(propertyName, propertyValue);
			builder.orderBy(orderBy, isOrderAsc);
			builder.offset(firstResult);
			builder.limit(maxResults);
			results = builder.query();
		} catch (SQLException e) {
			throwFatalException(e);
		}
		return results;
	}

	@Override
	public final List<E> findByProperties(final Map<String, Object> values, final String orderBy, final boolean isOrderAsc) {
		List<E> results = null;
		try {
			QueryBuilder<E, PK> builder = dao.queryBuilder();
			Where<E, PK> whereStatement = builder.where();
			boolean firsTimeflag = true;
			for (Map.Entry<String, Object> pair : values.entrySet()) {
				whereStatement.eq(pair.getKey(), pair.getValue());
			}
			whereStatement.and(values.size());
			builder.orderBy(orderBy, isOrderAsc);
			results = builder.query();
		} catch (SQLException e) {
			throwFatalException(e);
		}
		return results;
	}
	@Override
	public final List<E> findByProperties(final Map<String, Object> values, final String orderBy, final boolean isOrderAsc, final long firstResult, final long maxResults) {
		List<E> results = null;
		try {
			QueryBuilder<E, PK> builder = dao.queryBuilder();
			Where<E, PK> whereStatement = builder.where();
			boolean firsTimeflag = true;
			for (Map.Entry<String, Object> pair : values.entrySet()) {
				whereStatement.eq(pair.getKey(), pair.getValue());
			}
			whereStatement.and(values.size());
			builder.orderBy(orderBy, isOrderAsc);
			builder.offset(firstResult);
			builder.limit(maxResults);
			results = builder.query();
		} catch (SQLException e) {
			throwFatalException(e);
		}
		return results;
	}

}