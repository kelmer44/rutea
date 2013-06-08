package com.bretema.rutas.core.util;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.Uri;

import org.mapsforge.core.GeoPoint;

import java.util.Collection;
import java.util.zip.CRC32;

public class Constants {

	public static final float PI = 3.14159f;
	public static final String APP_PATH = "/sdcard/maps/";
	public static final String POIS_PATH = APP_PATH + "pois/";
	public static final String MEDIA_PATH = APP_PATH + "media/";
	public static final String IMAGE_MAP_ASSETS_PATH = MEDIA_PATH + "maps/";
	public static final String CONFIG_FILE = APP_PATH + "config.properties";
	
	
	public static final Integer GEO_INTENT_NAVIGATE = 0;
	public static final Integer GEO_INTENT_GEO = 1;
	public static final Integer CURRENT_INTENT_TYPE = GEO_INTENT_GEO;
	
	private static Intent launchGeoIntent(Integer tipo, String name, double lat, double lon){
	    Intent intent = new Intent();
	    if(tipo == GEO_INTENT_GEO){
	        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + lat + "," + lon));
	    }
	    else if (tipo == GEO_INTENT_NAVIGATE) 
	    {
	        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + lat + "," + lon));
	    }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
	}
	
	
	public static Intent launchGeoIntent(String name, double lat, double lon){
        return launchGeoIntent(CURRENT_INTENT_TYPE, name, lat, lon);
    }
    
	
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
    
    public static long calculateCrc32(String data){
    	
    	return calculateCrc32(data.getBytes());    	
    }
	public static long calculateCrc32(byte[] data){
		  CRC32 crc = new CRC32();
		  crc.update(data);
		  return crc.getValue();
		}
}
