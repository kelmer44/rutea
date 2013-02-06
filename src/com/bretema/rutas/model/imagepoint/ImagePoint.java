package com.bretema.rutas.model.imagepoint;

import java.util.ArrayList;
import java.util.List;

public class ImagePoint {

	private int x;
	private int y;
	
	private String texto;
	
	private List<String> images;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	
	public ImagePoint(int x, int y, String texto, List<String> images) {
		super();
		this.x = x;
		this.y = y;
		this.texto = texto;
		this.images = images;
		
	}
	
	
	public ImagePoint(int x, int y, String texto) {
		this(x, y, texto, new ArrayList<String>());
	}

	public ImagePoint() {
		super();
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}
	
	
}
