package com.bretema.rutas.enums;

public enum PoiType {
	SimplePoi(0), FarmaciaPoi(1), RestaurantePoi(2);
	
	PoiType(int position){
		this.position = position;
	}
	private int position;
	
	public int getPosition(){
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
