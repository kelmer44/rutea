package com.bretema.rutas.activities;

import org.mapsforge.android.maps.overlay.OverlayItem;
import org.mapsforge.core.GeoPoint;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Proporciona la posición actual del usuario. 
 * He tenido que hacerlo asi debido a que usar una inner class 
 * produce un memory leak, al igual que implementar la interfaz
 * directamente
 * @author kelmer
 *
 */
public class MyLocationListener implements LocationListener {

	private OverlayItem overlayItem;
	
	/**
	 * Overlay item que representa la posición del usuario
	 * cuya posición se cambiará cada vez que el usuario se
	 * mueva.
	 * @param meOverlayItem
	 */
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
