/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bretema.rutas.map;

import java.util.ArrayList;

import org.mapsforge.android.maps.MapView;
import org.mapsforge.android.maps.Projection;
import org.mapsforge.android.maps.overlay.ItemizedOverlay;
import org.mapsforge.android.maps.overlay.Overlay;
import org.mapsforge.android.maps.overlay.OverlayItem;
import org.mapsforge.core.GeoPoint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 *
 * @author Anton Ugnich; http://anton.ugnich.com
 */
public class MyLocationOverlay extends ItemizedOverlay<OverlayItem> implements LocationListener {

	
    private Context context;
    private MapView mapView;
    private Location lastFix = null;
    private Point cachedCenterPosition;
    private byte cachedZoomLevel;
    private OverlayItem me_overlay;

    public MyLocationOverlay(Drawable marker, Context context, MapView mapView) {
        super(boundCenter(marker));
        this.context = context;
        this.mapView = mapView;
        me_overlay = new OverlayItem();
    }

    public void onLocationChanged(Location location) {
        lastFix = location;
        me_overlay.setPoint(getMyLocation());
        super.requestRedraw();
    }


    public boolean enableMyLocation() {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        String bestProvider = lm.getBestProvider(new Criteria(), true);
        if (bestProvider != null && !bestProvider.equals("")) {
            lm.requestLocationUpdates(bestProvider, 0, 0, this);
            return true;
        } else {
            return false;
        }
    }

    public void disableMyLocation() {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        lm.removeUpdates(this);
    }

    protected boolean dispatchTap() {
        return false;
    }

    public GeoPoint getMyLocation() {
        if (lastFix != null) {
            return new GeoPoint(lastFix.getLatitude(), lastFix.getLongitude());
        } else {
            return null;
        }
    }

    public Location getLastFix() {
        return lastFix;
    }

    public boolean enableCompass() {
        return false;
    }

    public void disableCompass() {
    }

    public void onProviderDisabled(String provider) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

	@Override
	protected OverlayItem createItem(int i) {
		return me_overlay;
	}

	@Override
	public int size() {
		return 1;
	}

}