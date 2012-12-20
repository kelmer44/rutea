package com.bretema.rutas.core.util;


public class Constants {

	private static final float PI = 3.14159f;
	
	
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

}
