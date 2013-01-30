package com.bretema.rutas.enums;

import com.bretema.rutas.R;

public enum MMType {
	Imagen(0), Video(1), LabeledImage(2);

	MMType(int position) {
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
