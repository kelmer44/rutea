package com.bretema.rutas.core.util;

import com.bretema.rutas.R;
import java.util.Collection;

import org.mapsforge.core.GeoPoint;

public class Constants {

	private static final float PI = 3.14159f;
	
	
	public static double geoLatDegToDouble(int deg, int min, float sec, boolean north){
		
		double val = deg + min/60 + sec/3600;
		if(!north) 
			val=-val;
		return val;
	}
	
	
	public static double geoLonDegToDouble(int deg, int min, float sec, boolean east){
		
		double val = deg + min/60 + sec/3600;
		if(!east) 
			val=-val;
		return val;
	}

	/**
     * Transforms a collection of GeoPoints to an array.
     * 
     * @param collection the geo points
     * @return array of geo points
     */
    public static GeoPoint[][] toGeoPointArray(Collection<GeoPoint> collection) {
 
        if (collection == null || collection.size() == 0) {
            return new GeoPoint[][] {};
        }
 
        GeoPoint[][] result = new GeoPoint[1][collection.size()];
 
        int i = 0;
        for (GeoPoint geoPoint : collection) {
            result[0][i++] = geoPoint;
        }
        return result;
    }
}
