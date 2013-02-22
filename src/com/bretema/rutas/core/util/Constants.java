package com.bretema.rutas.core.util;

import java.util.Collection;

import org.mapsforge.core.GeoPoint;

import android.content.res.AssetManager;
import android.graphics.Typeface;

public class Constants {

	public static final float PI = 3.14159f;
	public static final String appPath = "/sdcard/maps/";
	public static final String poisPath = appPath + "pois/";
	public static final String mediaPath = appPath + "media/";
	public static final String imageMapAssetsPath = mediaPath + "maps/";
	
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
    
    
    public static Typeface getTitleFont(AssetManager mgr){
		return Typeface.createFromAsset(mgr, "YanoneKaffeesatz-Light.ttf");
    }
    
    public static Typeface getTitleFontRegular(AssetManager mgr){
		return Typeface.createFromAsset(mgr, "YanoneKaffeesatz-Regular.ttf");
    }
    
    public static Typeface getTextFont(AssetManager mgr){
		return Typeface.createFromAsset(mgr, "ColabReg.ttf");
    }
    
    public static Typeface getSubtitleFont(AssetManager mgr){
		return Typeface.createFromAsset(mgr, "ColabMed.ttf");
    }
}
