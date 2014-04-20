package com.bretema.rutas.map;

import com.bretema.rutas.enums.PoiType;
import com.bretema.rutas.model.poi.Poi;

import org.mapsforge.android.maps.overlay.OverlayItem;
import org.mapsforge.core.GeoPoint;

import java.util.Locale;

public class PoiOverlayItem extends OverlayItem {

	private Poi associatedPoi;
	boolean selectable = false;
	private Locale mLocale;
	
	public PoiOverlayItem(Locale l){
		super();
		this.mLocale = l;
	}
	
	public PoiOverlayItem(Locale l, Poi poi){
		super(new GeoPoint(poi.getLatitude(), poi.getLongitude()), poi.getNombreByLocale(l), poi.getDescByLocale(l));
		if(poi.getTipo() == PoiType.SimplePoi || poi.getTipo() == PoiType.SecondaryPoi)
			selectable = true;
		associatedPoi = poi;
	}

	public Poi getAssociatedPoi() {
		return associatedPoi;
	}

	public void setAssociatedPoi(Poi associatedPoi) {
		this.associatedPoi = associatedPoi;
	}

	public boolean isSelectable() {
		return selectable;
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}
}
