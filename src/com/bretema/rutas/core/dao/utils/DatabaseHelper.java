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
 * Database helper class is used to manage the creation and upgrading of your database
 * 
 * @param <T> Entity.
 * @param <ID> ID type.
 */
public class DatabaseHelper<T, ID> extends OrmLiteSqliteOpenHelper {
    /**
     * Logging
     */
    private static final String LOG_TAG = DatabaseHelper.class.getSimpleName();

	/**
	 * name of the database file for your application -- change to something appropriate for your app
	 */
    public static final String DATABASE = "rutas.db";
    /**
     *  any time you make changes to your database objects, you may have to increase the database version
     */
    public static final int VERSION = 3;
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
     * @param context The context.
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE, null, VERSION);
        this.context = context;
        Log.i(LOG_TAG, "Installing database, databasename = " + DATABASE + ", version = " + VERSION);
    }

    /**
     * Create a new database helper.
     * @param context The context.
     * @param databaseName The database name.
     * @param factory The factory.
     * @param databaseVersion The database version.
     */
    public DatabaseHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
        this.context = context;
        Log.i(LOG_TAG, "Installing database, databasename = " + databaseName + ", version = " + databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.d(LOG_TAG, "Creating the database");
            Log.d(LOG_TAG, "Database path: " + database.getPath());
            for(Tables table : Tables.values()) {
                TableUtils.createTable(connectionSource, table.getTableClass());
            }
            
            insertDefaultData(database);
            
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Excpetion while creating the database", e);
            throw new RuntimeException("Excpetion while creating the database", e);
        }
    }
    
    
    public void insertDefaultData(SQLiteDatabase database) {
        int defaultProjectId = 1;

        Log.d(LOG_TAG, "Inserting default route");
        ContentValues routeValues = new ContentValues();
        routeValues.put("id", 1);
        routeValues.put("nombre", "Ribeira Sacra");
        routeValues.put("description", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        routeValues.put("mainImagePath", "ruta1/rsa.jpg");
        routeValues.put("shortDescription", "Un roteiro polos cañóns do Sil.");
        database.insert("ruta", null, routeValues);

        Log.d(LOG_TAG, "Inserting default POIS for route 1");
        ContentValues poiValues = new ContentValues();
        poiValues.put("nombre", "San Pedro de Rocas");
        poiValues.put("descripcion", "Pues aquí iría la descripción del punto");
        poiValues.put("tipo", PoiType.SimplePoi.toString());
        poiValues.put("latitude", Constants.geoLatDegToDouble(42, 20, 30.70f, true));
        poiValues.put("longitude", Constants.geoLonDegToDouble(7, 42, 48.66f, false));
        database.insert("poi", null, poiValues);
        
        ContentValues poiValues2 = new ContentValues();
        poiValues2.put("nombre", "Santo Estevo de Ribas de Sil");
        poiValues2.put("descripcion", "Pues aquí iría la descripción del punto");
        poiValues2.put("tipo", PoiType.SimplePoi.toString());
        poiValues2.put("latitude", Constants.geoLatDegToDouble(42, 25, 1.30f, true));
        poiValues2.put("longitude", Constants.geoLonDegToDouble(7, 41, 10.33f, false));
        database.insert("poi", null, poiValues2);
        
        ContentValues poiValues3 = new ContentValues();
        poiValues3.put("nombre", "Santa Cristina de Ribas de Sil");
        poiValues3.put("descripcion", "Pues aquí iría la descripción del punto");
        poiValues3.put("tipo", PoiType.SimplePoi.toString());
        poiValues3.put("latitude", Constants.geoLatDegToDouble(42, 23, 42.87f, true));
        poiValues3.put("longitude", Constants.geoLonDegToDouble(7, 35, 19.39f, false));
        database.insert("poi", null, poiValues3);
        
        ContentValues poiValues4 = new ContentValues();
        poiValues4.put("nombre", "Balcones de Madrid");
        poiValues4.put("descripcion", "Pues aquí iría la descripción del punto");
        poiValues4.put("tipo", PoiType.SimplePoi.toString());
        poiValues4.put("latitude", Constants.geoLatDegToDouble(42, 23, 20.33f, true));
        poiValues4.put("longitude", Constants.geoLonDegToDouble(7, 33, 59.67f, false));
        database.insert("poi", null, poiValues4);
        
        ContentValues poiValues5 = new ContentValues();
        poiValues5.put("nombre", "Barxacova");
        poiValues5.put("descripcion", "Pues aquí iría la descripción del punto");
        poiValues5.put("tipo", PoiType.SimplePoi.toString());
        poiValues5.put("latitude", Constants.geoLatDegToDouble(42, 22, 54.68f, true));
        poiValues5.put("longitude", Constants.geoLonDegToDouble(7, 29, 47.69f, false));
        database.insert("poi", null, poiValues5);
    }

    
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {


       // This is the old code for upgrading a database: dropping the old one and creating a new one...
       try {

           for(Tables table : Tables.values()) {
               TableUtils.dropTable(connectionSource, table.getTableClass(), true);
           }
           onCreate(database);
       } catch (SQLException e) {
           Log.e(LOG_TAG, "Excpetion while updating the database from version " + oldVersion + "to " + newVersion, e);
           throw new RuntimeException("Excpetion while updating the database from version " + oldVersion + "to " + newVersion, e);
       }
//        if (newVersion < oldVersion) {
//            Log.w(LOG_TAG, "Trying to install an older database over a more recent one. Not executing update...");
//            Log.d(LOG_TAG, "Database path: " + database.getPath());
//            return;
//        }
//
//        Log.d(LOG_TAG, "Updating the database from version " + oldVersion + " to " + newVersion);
//        Log.d(LOG_TAG, "Database path: " + database.getPath());
//
//        DatabaseUpgrade[] databaseUpgrades = DatabaseUpgrade.values();
//        int upgradeSqlCount = 0;
//        int upgradeSqlBlockCount = 0;
//        for (DatabaseUpgrade databaseUpgrade : databaseUpgrades) {
//            if (oldVersion < databaseUpgrade.getToVersion()) {
//                String[] queries = databaseUpgrade.getSqlQueries();
//                for (String query : queries) {
//                    try {
//                        database.execSQL(query);
//                    } catch (android.database.SQLException e) {
//                        Log.d(LOG_TAG, "Exception while executing upgrade queries (toVersion: "
//                                + databaseUpgrade.getToVersion() + ") during query: " + query, e);
//                        throw new RuntimeException("Exception while executing upgrade queries (toVersion: "
//                                + databaseUpgrade.getToVersion() + ") during query: " + query, e);
//                    }
//                    Log.d(LOG_TAG, "Executed an upgrade query to version " + databaseUpgrade.getToVersion()
//                            + " with success: " + query);
//                    upgradeSqlCount++;
//                }
//                Log.d(LOG_TAG, "Upgrade queries for version " + databaseUpgrade.getToVersion()
//                        + " executed with success");
//                upgradeSqlBlockCount++;
//            }
//        }
//        if (upgradeSqlCount > 0) {
//            Log.d(LOG_TAG, "All upadate queries exected with success. Total number of upgrade queries executed: "
//                    + upgradeSqlCount + " in " + upgradeSqlBlockCount);
//        } else {
//            Log.d(LOG_TAG, "No database upgrade queries where necessary!");
//        }
    }

    @Override
    public void close() {
        Log.d(LOG_TAG, "Closing connection");
        super.close();
    }

    public static Date convertDateToSqliteDate(Date date) {
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