package com.bretema.rutas.activities;

import org.mapsforge.android.maps.overlay.OverlayItem;
import org.mapsforge.core.GeoPoint;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class MyLocationListener implements LocationListener {

	private OverlayItem overlayItem;
	
	public MyLocationListener(OverlayItem meOverlayItem) {
		super();
		this.overlayItem = meOverlayItem;
	}

	@Override
	public void onLocationChanged(Location location) {
		overlayItem.setPoint(new GeoPoint(location.getLatitude(), location.getLongitude()));

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}
