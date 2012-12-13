package com.bretema.rutas.enums;

/**
 * Restringe los tipos posibles de Puntos de interés
 * @author Gabriel Sanmartín Díaz
 *
 */
public enum PoiType {
	SimplePoi(0), FarmaciaPoi(1), RestaurantePoi(2), CentroSaludPoi(3), PoliciaPoi(
			4), GasolineraPoi(5), BarPoi(6), OITPoi(7);

	PoiType(int position) {
		this.position = position;
	}

	private int	position;

	public int getPosition() {
		return position;
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
}
