package com.bretema.rutas.core.dao.utils;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bretema.rutas.R;
import com.bretema.rutas.core.util.Constants;
import com.bretema.rutas.enums.MMType;
import com.bretema.rutas.enums.PoiType;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.db.SqliteAndroidDatabaseType;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * A utility class to be used to setup and interact with a database.
 * 
 * Database helper class is used to manage the creation and upgrading of your
 * database
 * 
 * @param <T>
 *            Entity.
 * @param <ID>
 *            ID type.
 */
public class DatabaseHelper<T, ID> extends OrmLiteSqliteOpenHelper {
	/**
	 * Logging
	 */
	private static final String LOG_TAG = DatabaseHelper.class.getSimpleName();

	/**
	 * name of the database file for your application -- change to something
	 * appropriate for your app
	 */
	public static final String DATABASE = "rutas.db";
	/**
	 * any time you make changes to your database objects, you may have to
	 * increase the database version
	 */
	public static final int VERSION = 25;
	/**
	 * The database type.
	 */
	private DatabaseType databaseType = new SqliteAndroidDatabaseType();

	/**
	 * The context.
	 */
	private Context context = null;

	private Map<String, Dao<T, ID>> daoCache = new HashMap<String, Dao<T, ID>>();

	/**
	 * Create a new database helper.
	 * 
	 * @param context
	 *            The context.
	 */
	public DatabaseHelper(final Context context) {
		super(context, DATABASE, null, VERSION);
		this.context = context;
		Log.i(LOG_TAG, "Installing database, databasename = " + DATABASE
				+ ", version = " + VERSION);
	}

	/**
	 * Create a new database helper.
	 * 
	 * @param context
	 *            The context.
	 * @param databaseName
	 *            The database name.
	 * @param factory
	 *            The factory.
	 * @param databaseVersion
	 *            The database version.
	 */
	public DatabaseHelper(final Context context, final String databaseName,
			final SQLiteDatabase.CursorFactory factory,
			final int databaseVersion) {
		super(context, databaseName, factory, databaseVersion);
		this.context = context;
		Log.i(LOG_TAG, "Installing database, databasename = " + databaseName
				+ ", version = " + databaseVersion);
	}

	@Override
	public final void onCreate(final SQLiteDatabase database,
			final ConnectionSource connectionSource) {
		try {
			Log.d(LOG_TAG, "Creating the database");
			Log.d(LOG_TAG, "Database path: " + database.getPath());
			for (Tables table : Tables.values()) {
				TableUtils.createTable(connectionSource, table.getTableClass());
			}

			insertDefaultData(database);

		} catch (SQLException e) {
			Log.e(LOG_TAG, "Excpetion while creating the database", e);
			throw new RuntimeException("Excpetion while creating the database",
					e);
		}
	}

	/**
	 * Inserta datos de prueba.
	 * 
	 * @param database
	 *            base de datos sobre la que se introducirán estos
	 */
	public final void insertDefaultData(final SQLiteDatabase database) {

		Log.d(LOG_TAG, "Inserting default route");
		ContentValues routeValues = new ContentValues();
		routeValues.put("id", 1);
		routeValues.put("nombre", "La Ribeira Sacra en un día");
		routeValues
				.put("description",
						"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\nDonec molestie eros et ante aliquet vitae imperdiet odio dictum. Maecenas tempor, mi vitae fermentum posuere, mi nibh malesuada lacus, id dictum purus eros quis nisi. Curabitur vel dolor sem, a iaculis ante. Curabitur nisl dolor, adipiscing at egestas sit amet, adipiscing ut mauris. Aenean quis leo dignissim ante luctus gravida. Sed metus neque, convallis non vulputate sed, blandit nec turpis. Fusce ac lacinia sapien. Morbi nec mauris eget turpis facilisis vulputate nec non urna. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Maecenas auctor pretium leo, at iaculis tortor lacinia nec. Suspendisse nec viverra mi. Nulla imperdiet blandit nisl, sed condimentum elit porta non.");
		routeValues.put("mainImagePath", "ruta1/rsa.jpg");
		routeValues.put("shortDescription", "Descripción de esta ruta.");
		routeValues.put("routeFile", "ruta1/pruebaruta.gpx");
		database.insert("ruta", null, routeValues);

		ContentValues routeValues2 = new ContentValues();
		routeValues2.put("id", 2);
		routeValues2.put("nombre", "La Ribeira Sacra del Sil");
		routeValues2
				.put("description",
						"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
		routeValues2.put("mainImagePath", "ruta2/rsa.jpg");
		routeValues2.put("shortDescription", "Descripción de esta ruta 2.");
		routeValues2.put("routeFile", "ruta1/pruebaruta.gpx");
		database.insert("ruta", null, routeValues2);

		ContentValues routeValues3 = new ContentValues();
		routeValues3.put("id", 4);
		routeValues3.put("nombre", "Románico en la Ribeira Sacra (Día 1)");
		routeValues3
				.put("description",
						"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
		routeValues3.put("mainImagePath", "ruta4/rsa.jpg");
		routeValues3.put("shortDescription", "Descripción de esta ruta 3.");
		routeValues3.put("routeFile", "ruta1/pruebaruta.gpx");
		database.insert("ruta", null, routeValues3);

		ContentValues routeValues4 = new ContentValues();
		routeValues4.put("id", 5);
		routeValues4.put("nombre", "Románico en la Ribeira Sacra (Día 2)");
		routeValues4
				.put("description",
						"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
		routeValues4.put("mainImagePath", "ruta5/rsa.jpg");
		routeValues4.put("shortDescription", "Descripción de esta ruta 4.");
		routeValues4.put("routeFile", "ruta1/pruebaruta.gpx");
		database.insert("ruta", null, routeValues4);

		ContentValues routeValues5 = new ContentValues();
		routeValues5.put("id", 3);
		routeValues5.put("nombre", "La Ribeira Sacra del Miño");
		routeValues5
				.put("description",
						"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
		routeValues5.put("mainImagePath", "ruta3/rsa.jpg");
		routeValues5.put("shortDescription", "Descripción de esta ruta 3.");
		routeValues5.put("routeFile", "ruta1/pruebaruta.gpx");
		database.insert("ruta", null, routeValues5);

		
		ContentValues routeValues6 = new ContentValues();
		routeValues6.put("id", 6);
		routeValues6.put("nombre", "Cultura, Naturaleza y Enología de la Ribeira Sacra");
		routeValues6
				.put("description",
						"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
		routeValues6.put("mainImagePath", "ruta6/rsa.jpg");
		routeValues6.put("shortDescription", "Descripción de esta ruta 6.");
		routeValues6.put("routeFile", "ruta1/pruebaruta.gpx");
		database.insert("ruta", null, routeValues6);
		
		
		Log.d(LOG_TAG, "Inserting default POIS for route 1");
		ContentValues poiValues = new ContentValues();
		poiValues.put("id", 1);
		poiValues.put("nombre", "San Pedro de Rocas");
		poiValues.put("descripcion", "Aenean est diam, mollis sed tempus non, laoreet et est.");
		poiValues.put("tipo", PoiType.SimplePoi.toString());
		poiValues.put("latitude", 42.341861);
		poiValues.put("longitude", -7.713517);
		poiValues.put("orden", 1);
		poiValues.put("rutaId", 1);
		database.insert("poi", null, poiValues);

		ContentValues poiValues2 = new ContentValues();
		poiValues2.put("id", 2);
		poiValues2.put("nombre", "Santo Estevo de Ribas de Sil");
		poiValues2
				.put("descripcion", "Quisque non quam mauris, eget faucibus nisi.");
		poiValues2.put("tipo", PoiType.SimplePoi.toString());
		poiValues2.put("latitude", 42.417028);
		poiValues2.put("longitude", -7.686203);
		poiValues2.put("orden", 2);
		poiValues2.put("rutaId", 1);
		database.insert("poi", null, poiValues2);

		ContentValues poiValues3 = new ContentValues();
		poiValues3.put("id", 3);
		poiValues3.put("nombre", "Santa Cristina de Ribas de Sil");
		poiValues3
				.put("descripcion", "Sed quis ligula at ligula porttitor vehicula.");
		poiValues3.put("tipo", PoiType.SimplePoi.toString());
		poiValues3.put("latitude", 42.395242);
		poiValues3.put("longitude", -7.588719);
		poiValues3.put("orden", 3);
		poiValues3.put("rutaId", 1);
		database.insert("poi", null, poiValues3);

		ContentValues poiValues4 = new ContentValues();
		poiValues4.put("id", 4);
		poiValues4.put("nombre", "Balcones de Madrid");
		poiValues4
				.put("descripcion", "Morbi orci sem, posuere ut tincidunt vel, elementum a magna.");
		poiValues4.put("tipo", PoiType.SimplePoi.toString());
		poiValues4.put("latitude", 42.388981);
		poiValues4.put("longitude", -7.566575);
		poiValues4.put("orden", 4);
		poiValues4.put("rutaId", 1);
		database.insert("poi", null, poiValues4);

		ContentValues poiValues5 = new ContentValues();
		poiValues5.put("id", 5);
		poiValues5.put("nombre", "Barxacova");
		poiValues5
				.put("descripcion", "Quisque gravida, risus id consequat pellentesque.");
		poiValues5.put("tipo", PoiType.SimplePoi.toString());
		poiValues5.put("latitude", 42.381856);
		poiValues5.put("longitude", -7.496581);
		poiValues5.put("orden", 5);
		poiValues5.put("rutaId", 1);
		database.insert("poi", null, poiValues5);

		ContentValues farmaciaPoi = new ContentValues();
		farmaciaPoi.put("id", 6);
		farmaciaPoi.put("nombre", "Farmacia Ramírez");
		farmaciaPoi
				.put("descripcion", "Pues aquí iría la descripción del punto");
		farmaciaPoi.put("tipo", PoiType.FarmaciaPoi.toString());
		farmaciaPoi.put("latitude", 42.376173);
		farmaciaPoi.put("longitude", -7.584356);
		farmaciaPoi.put("orden", 6);
		farmaciaPoi.put("rutaId", 1);
		database.insert("poi", null, farmaciaPoi);
	
		ContentValues restaurantePoi = new ContentValues();
		restaurantePoi.put("id", 7);
		restaurantePoi.put("nombre", "Restaurante Pancracio");
		restaurantePoi
				.put("descripcion", "Pues aquí iría la descripción del punto");
		restaurantePoi.put("tipo", PoiType.RestaurantePoi.toString());
		restaurantePoi.put("latitude", 				42.369452);
		restaurantePoi.put("longitude", -7.580401);
		restaurantePoi.put("orden", 7);
		restaurantePoi.put("rutaId", 1);
		database.insert("poi", null, restaurantePoi);
		
		
		ContentValues mmValue21 = new ContentValues();
		mmValue21.put("uri", "ruta1/imagen4.jpg");
		mmValue21.put("thumbUri", "ruta1/thumb4.jpg");
		mmValue21.put("nombre", "San Pedro de Rocas");
		mmValue21.put("descripcion", "Fachada");
		mmValue21.put("tipo", MMType.Imagen.toString());
		mmValue21.put("poiId", 1);
		mmValue21.put("orden", 1);
		database.insert("multimedia", null, mmValue21);

		ContentValues mmValue22 = new ContentValues();
		mmValue22.put("uri", "ruta1/imagen5.jpg");
		mmValue22.put("thumbUri", "ruta1/thumb5.jpg");
		mmValue22.put("nombre", "San Pedro de Rocas");
		mmValue22.put("descripcion", "Gárgola");
		mmValue22.put("tipo", MMType.Imagen.toString());
		mmValue22.put("poiId", 1);
		mmValue22.put("orden", 2);
		database.insert("multimedia", null, mmValue22);

		ContentValues mmValue10 = new ContentValues();
		mmValue10.put("uri", "ruta1/imagen9.jpg");
		mmValue10.put("thumbUri", "ruta1/thumb9.jpg");
		mmValue10.put("nombre", "Santo Estevo de Ribas de Sil");
		mmValue10.put("descripcion", "Patio interior");
		mmValue10.put("tipo", MMType.Imagen.toString());
		mmValue10.put("poiId", 2);
		mmValue10.put("orden", 3);
		database.insert("multimedia", null, mmValue10);

		ContentValues mmValue11 = new ContentValues();
		mmValue11.put("uri", "ruta1/imagen10.jpg");
		mmValue11.put("thumbUri", "ruta1/thumb10.jpg");
		mmValue11.put("nombre", "Santo Estevo de Ribas de Sil");
		mmValue11.put("descripcion", "Detalle fachada");
		mmValue11.put("tipo", MMType.Imagen.toString());
		mmValue11.put("poiId", 2);
		mmValue11.put("orden", 4);
		database.insert("multimedia", null, mmValue11);

		ContentValues mmValue31 = new ContentValues();
		mmValue31.put("uri", "ruta1/imagen6.jpg");
		mmValue31.put("thumbUri", "ruta1/thumb6.jpg");
		mmValue31.put("nombre", "Santa Cristina de Ribas de Sil 1");
		mmValue31.put("descripcion", "Arcos");
		mmValue31.put("tipo", MMType.Imagen.toString());
		mmValue31.put("poiId", 3);
		mmValue31.put("orden", 5);
		database.insert("multimedia", null, mmValue31);

		ContentValues mmValue32 = new ContentValues();
		mmValue32.put("uri", "ruta1/imagen7.jpg");
		mmValue32.put("thumbUri", "ruta1/thumb7.jpg");
		mmValue32.put("nombre", "Santa Cristina de Ribas de Sil 2");
		mmValue32.put("descripcion", "Soportal");
		mmValue32.put("tipo", MMType.Imagen.toString());
		mmValue32.put("poiId", 3);
		mmValue32.put("orden", 6);
		database.insert("multimedia", null, mmValue32);

		ContentValues mmValue33 = new ContentValues();
		mmValue33.put("uri", "ruta1/imagen8.jpg");
		mmValue33.put("thumbUri", "ruta1/thumb8.jpg");
		mmValue33.put("nombre", "Santa Cristina de Ribas de Sil 3");
		mmValue33.put("descripcion", "Glifo");
		mmValue33.put("tipo", MMType.Imagen.toString());
		mmValue33.put("poiId", 3);
		mmValue33.put("orden", 7);
		database.insert("multimedia", null, mmValue33);

		ContentValues mmValue41 = new ContentValues();
		mmValue41.put("uri", "ruta1/imagen1.jpg");
		mmValue41.put("thumbUri", "ruta1/thumb1.jpg");
		mmValue41.put("nombre", "Balcons de Madrid");
		mmValue41.put("descripcion", "Pano");
		mmValue41.put("tipo", MMType.Imagen.toString());
		mmValue41.put("poiId", 4);
		mmValue41.put("orden", 8);
		database.insert("multimedia", null, mmValue41);

		ContentValues mmValue42 = new ContentValues();
		mmValue42.put("uri", "ruta1/imagen2.jpg");
		mmValue42.put("thumbUri", "ruta1/thumb2.jpg");
		mmValue42.put("nombre", "Balcons de Madrid");
		mmValue42.put("descripcion", "Pano");
		mmValue42.put("tipo", MMType.Imagen.toString());
		mmValue42.put("poiId", 4);
		mmValue42.put("orden", 9);
		database.insert("multimedia", null, mmValue42);

		ContentValues mmValue51 = new ContentValues();
		mmValue51.put("uri", "ruta1/imagen2.jpg");
		mmValue51.put("thumbUri", "ruta1/thumb3.jpg");
		mmValue51.put("nombre", "Barxacova");
		mmValue51.put("descripcion", "Vexetación");
		mmValue51.put("tipo", MMType.Imagen.toString());
		mmValue51.put("poiId", 5);
		mmValue51.put("orden", 10);
		database.insert("multimedia", null, mmValue51);
	}

	@Override
	public final void onUpgrade(final SQLiteDatabase database,
			final ConnectionSource connectionSource, final int oldVersion,
			final int newVersion) {

		// This is the old code for upgrading a database: dropping the old one
		// and creating a new one...
		try {

			for (Tables table : Tables.values()) {
				TableUtils.dropTable(connectionSource, table.getTableClass(),
						true);
			}
			onCreate(database);
		} catch (SQLException e) {
			Log.e(LOG_TAG,
					"Excpetion while updating the database from version "
							+ oldVersion + "to " + newVersion, e);
			throw new RuntimeException(
					"Excpetion while updating the database from version "
							+ oldVersion + "to " + newVersion, e);
		}
		// if (newVersion < oldVersion) {
		// Log.w(LOG_TAG,
		// "Trying to install an older database over a more recent one. Not executing update...");
		// Log.d(LOG_TAG, "Database path: " + database.getPath());
		// return;
		// }
		//
		// Log.d(LOG_TAG, "Updating the database from version " + oldVersion +
		// " to " + newVersion);
		// Log.d(LOG_TAG, "Database path: " + database.getPath());
		//
		// DatabaseUpgrade[] databaseUpgrades = DatabaseUpgrade.values();
		// int upgradeSqlCount = 0;
		// int upgradeSqlBlockCount = 0;
		// for (DatabaseUpgrade databaseUpgrade : databaseUpgrades) {
		// if (oldVersion < databaseUpgrade.getToVersion()) {
		// String[] queries = databaseUpgrade.getSqlQueries();
		// for (String query : queries) {
		// try {
		// database.execSQL(query);
		// } catch (android.database.SQLException e) {
		// Log.d(LOG_TAG,
		// "Exception while executing upgrade queries (toVersion: "
		// + databaseUpgrade.getToVersion() + ") during query: " + query, e);
		// throw new
		// RuntimeException("Exception while executing upgrade queries (toVersion: "
		// + databaseUpgrade.getToVersion() + ") during query: " + query, e);
		// }
		// Log.d(LOG_TAG, "Executed an upgrade query to version " +
		// databaseUpgrade.getToVersion()
		// + " with success: " + query);
		// upgradeSqlCount++;
		// }
		// Log.d(LOG_TAG, "Upgrade queries for version " +
		// databaseUpgrade.getToVersion()
		// + " executed with success");
		// upgradeSqlBlockCount++;
		// }
		// }
		// if (upgradeSqlCount > 0) {
		// Log.d(LOG_TAG,
		// "All upadate queries exected with success. Total number of upgrade queries executed: "
		// + upgradeSqlCount + " in " + upgradeSqlBlockCount);
		// } else {
		// Log.d(LOG_TAG, "No database upgrade queries where necessary!");
		// }
	}

	@Override
	public final void close() {
		Log.d(LOG_TAG, "Closing connection");
		super.close();
	}

	/**
	 * Convierte a formato de fecha de SQLite.
	 * 
	 * @param date
	 *            fecha en formato regular
	 * @return Fecha formateada
	 */
	public static Date convertDateToSqliteDate(final Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}
}
