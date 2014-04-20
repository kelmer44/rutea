package com.bretema.rutas.core.dao.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bretema.rutas.model.codigo.Codigo;
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
	private static final String		LOG_TAG			= DatabaseHelper.class.getSimpleName();

	/**
	 * name of the database file for your application -- change to something
	 * appropriate for your app
	 */
	public static final String		DATABASE		= "rutas.db";
	/**
	 * any time you make changes to your database objects, you may have to
	 * increase the database version
	 */
	public static final int			VERSION			= 98;
	/**
	 * The database type.
	 */
	private DatabaseType			databaseType	= new SqliteAndroidDatabaseType();

	/**
	 * The context.
	 */
	private Context					context			= null;

	private Map<String, Dao<T, ID>>	daoCache		= new HashMap<String, Dao<T, ID>>();
	
	private boolean 				resetCodes = true;

	/**
	 * Create a new database helper.
	 * 
	 * @param context
	 *            The context.
	 */
	public DatabaseHelper(final Context context) {
		super(context, DATABASE, null, VERSION);
		this.context = context;
		Log.i(LOG_TAG, "Installing database, databasename = " + DATABASE + ", version = " + VERSION);
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
	public DatabaseHelper(final Context context, final String databaseName, final SQLiteDatabase.CursorFactory factory, final int databaseVersion) {
		super(context, databaseName, factory, databaseVersion);
		this.context = context;
		Log.i(LOG_TAG, "Installing database, databasename = " + databaseName + ", version = " + databaseVersion);
	}

	@Override
	public final void onCreate(final SQLiteDatabase database, final ConnectionSource connectionSource) {
		try {
			Log.d(LOG_TAG, "Creating the database");
			Log.d(LOG_TAG, "Database path: " + database.getPath());
			for (Tables table : Tables.values()) {
				TableUtils.createTable(connectionSource, table.getTableClass());
			}

			insertDefaultData(database);

		} catch (SQLException e) {
			Log.e(LOG_TAG, "Excpetion while creating the database", e);
			throw new RuntimeException("Excpetion while creating the database", e);
		}
	}

	/**
	 * Inserta datos de prueba.
	 * 
	 * @param database
	 *            base de datos sobre la que se introducir�n estos
	 */
	public final void insertDefaultData(final SQLiteDatabase database) {
		BufferedReader br = null;
	    try {
	        br = new BufferedReader(new InputStreamReader(context.getAssets().open("ruta.sql")), 1024 * 4);
	        String line = null;
	        database.beginTransaction();
	       // database.execSQL("INSERT INTO ruta (id) VALUES (1)");
	        while ((line = br.readLine()) != null) {
	        	Log.d(LOG_TAG, line);
	        	
	        	database.execSQL(line);
	        }
	        
	        
	        br = new BufferedReader(new InputStreamReader(context.getAssets().open("pois.sql")), 1024 * 4);
	        line = null;
	        while ((line = br.readLine()) != null) {
	        	Log.d(LOG_TAG, line);
	        	
	        	database.execSQL(line);
	        }
	        
	        
	        br = new BufferedReader(new InputStreamReader(context.getAssets().open("Multimedia.sql")), 1024 * 4);
	        line = null;
	        while ((line = br.readLine()) != null) {
	        	Log.d(LOG_TAG, line);
	        	
	        	database.execSQL(line);
	        }
	        
	        br = new BufferedReader(new InputStreamReader(context.getAssets().open("secondarypoi.sql")), 1024 * 4);
	        line = null;
	        while ((line = br.readLine()) != null) {
	        	Log.d(LOG_TAG, line);
	        	
	        	database.execSQL(line);
	        }
	        
	        br = new BufferedReader(new InputStreamReader(context.getAssets().open("mapimage.sql")), 1024 * 4);
	        line = null;
	        while ((line = br.readLine()) != null) {
	        	Log.d(LOG_TAG, line);
	        	
	        	database.execSQL(line);
	        }
	        
	        
	        database.setTransactionSuccessful();
	    } catch(android.database.SQLException s){
	    	Log.e(LOG_TAG, "Error SQL" + s);
	    }catch (IOException e) {
	        Log.e(LOG_TAG, "read database init file error");
	    } catch (Exception e2){
	    	 Log.e(LOG_TAG, e2.getMessage());
	    }finally {
	    	database.endTransaction();
	        if (br != null) {
	            try {
	                br.close();
	            } catch (IOException e) {
	                Log.e(LOG_TAG, "buffer reader close error");
	            }
	        }
	    }
	}

	@Override
	public final void onUpgrade(final SQLiteDatabase database, final ConnectionSource connectionSource, final int oldVersion, final int newVersion) {

		// This is the old code for upgrading a database: dropping the old one
		// and creating a new one...
		try {

			for (Tables table : Tables.values()) {
				//La tabla de c�digos no se toca!
				if(table!=Tables.CODIGOS || resetCodes){
					TableUtils.dropTable(connectionSource, table.getTableClass(), true);
				}
			}
			onCreate(database);
		} catch (SQLException e) {
			Log.e(LOG_TAG, "Excpetion while updating the database from version " + oldVersion + "to " + newVersion, e);
			throw new RuntimeException("Excpetion while updating the database from version " + oldVersion + "to " + newVersion, e);
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
