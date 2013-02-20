package com.bretema.rutas.service;

import java.util.List;

import com.bretema.rutas.model.mapimages.MapImage;
import com.bretema.rutas.model.poi.Poi;

public interface MapImageService {
	
	public List<MapImage> findAll();
	
	public MapImage getMapImage(Integer id);
	
	public List<MapImage> getMapImagesByMap(String mapName);
	
	public List<MapImage> getMapImagesByMapAndArea(String mapName, String areaId);
}
