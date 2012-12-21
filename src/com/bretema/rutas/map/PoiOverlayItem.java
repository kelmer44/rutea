package com.bretema.rutas.map;

import org.mapsforge.android.maps.overlay.OverlayItem;
import org.mapsforge.core.GeoPoint;

import com.bretema.rutas.enums.PoiType;
import com.bretema.rutas.model.poi.Poi;

public class PoiOverlayItem extends OverlayItem {

	private Poi associatedPoi;
	boolean selectable = false;
	
	public PoiOverlayItem(){
		super();
	}
	
	public PoiOverlayItem(Poi poi){
		super(new GeoPoint(poi.getLatitude(), poi.getLongitude()), poi.getNombre(), poi.getDescripcion());
		if(poi.getTipo() == PoiType.SimplePoi || poi.getTipo() == PoiType.SecondaryPoi)
			selectable = true;
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
