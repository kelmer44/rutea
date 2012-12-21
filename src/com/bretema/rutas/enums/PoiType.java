package com.bretema.rutas.enums;

import android.graphics.drawable.Drawable;

import com.bretema.rutas.R;

/**
 * Restringe los tipos posibles de Puntos de interés
 * @author Gabriel Sanmartín Díaz
 *
 */
public enum PoiType {
	
	SimplePoi(0,R.drawable.marker_green), FarmaciaPoi(1,R.drawable.red_cross, true), RestaurantePoi(2,R.drawable.restaurant_blue_2_48,true), CentroSaludPoi(3,R.drawable.marker_green), PoliciaPoi(
			4,R.drawable.marker_green), GasolineraPoi(5,R.drawable.marker_green), BarPoi(6,R.drawable.marker_green), OITPoi(7,R.drawable.marker_green), SecondaryPoi(8,R.drawable.marker_green);

	PoiType(int position, int drawableresource, boolean center) {
		this.position = position;
		this.drawableresource = drawableresource;
		this.isDrawableCenter = center;
	}

	
	PoiType(int position, int drawableresource) {
		this(position, drawableresource, false);
	}

	
	
	private int	position;
	private int drawableresource;
	private boolean isDrawableCenter = true;

	public int getPosition() {
		
		return position;
	}
	
	public int getDrawable(){
		return drawableresource;
	}

	public static PoiType getByIndex(int index) {
		PoiType[] poiTypes = PoiType.values();
		for (PoiType poiType : poiTypes) {
			if (poiType.getPosition() == index) {
				return poiType;
			}
		}

		return null;
	}

	public boolean isDrawableCenter() {
		return isDrawableCenter;
	}

	public void setDrawableCenter(boolean isDrawableCenter) {
		this.isDrawableCenter = isDrawableCenter;
	}
}
