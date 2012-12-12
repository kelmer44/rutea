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
	
	//Database filename
    public static final String DATABASE = "rutas.db";
    //Database version
    public static final int VERSION = 1;

    /**
     * Logging
     */
    private static final String LOG_TAG = DatabaseHelper.class.getSimpleName();

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
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Excpetion while creating the database", e);
            throw new RuntimeException("Excpetion while creating the database", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        if (newVersion < oldVersion) {
            Log.w(LOG_TAG, "Trying to install an older database over a more recent one. Not executing update...");
            Log.d(LOG_TAG, "Database path: " + database.getPath());
            return;
        }

        Log.d(LOG_TAG, "Updating the database from version " + oldVersion + " to " + newVersion);
        Log.d(LOG_TAG, "Database path: " + database.getPath());

        DatabaseUpgrade[] databaseUpgrades = DatabaseUpgrade.values();
        int upgradeSqlCount = 0;
        int upgradeSqlBlockCount = 0;
        for (DatabaseUpgrade databaseUpgrade : databaseUpgrades) {
            if (oldVersion < databaseUpgrade.getToVersion()) {
                String[] queries = databaseUpgrade.getSqlQueries();
                for (String query : queries) {
                    try {
                        database.execSQL(query);
                    } catch (android.database.SQLException e) {
                        Log.d(LOG_TAG, "Exception while executing upgrade queries (toVersion: "
                                + databaseUpgrade.getToVersion() + ") during query: " + query, e);
                        throw new RuntimeException("Exception while executing upgrade queries (toVersion: "
                                + databaseUpgrade.getToVersion() + ") during query: " + query, e);
                    }
                    Log.d(LOG_TAG, "Executed an upgrade query to version " + databaseUpgrade.getToVersion()
                            + " with success: " + query);
                    upgradeSqlCount++;
                }
                Log.d(LOG_TAG, "Upgrade queries for version " + databaseUpgrade.getToVersion()
                        + " executed with success");
                upgradeSqlBlockCount++;
            }
        }
        if (upgradeSqlCount > 0) {
            Log.d(LOG_TAG, "All upadate queries exected with success. Total number of upgrade queries executed: "
                    + upgradeSqlCount + " in " + upgradeSqlBlockCount);
        } else {
            Log.d(LOG_TAG, "No database upgrade queries where necessary!");
        }


        /* This is the old code for upgrading a database: dropping the old one and creating a new one...
        try {

            for(Tables table : Tables.values()) {
                TableUtils.dropTable(databaseType, connectionSource, table.getTableClass(), true);
            }
            onCreate(database);
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Excpetion while updating the database from version " + oldVersion + "to " + newVersion, e);
            throw new RuntimeException("Excpetion while updating the database from version " + oldVersion + "to " + newVersion, e);
        }*/
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