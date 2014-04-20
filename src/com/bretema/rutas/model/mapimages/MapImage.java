package com.bretema.rutas.model.mapimages;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Locale;

/**
 * Define imagenes asociadas a los puntos interactivos de otras imagenes
 * 
 * @author Gabriel Sanmart�n D�az
 * 
 */
@DatabaseTable
public class MapImage {

	@DatabaseField(generatedId = true)
	private Integer	id;

	/**
	 * Nombre del mapa dentro del map.xml de assets
	 */
	@DatabaseField
	private String mapa;
	
	/**
	 * Nombre del punto dentro de la imagen interactiva al que asociamos la imagen
	 */
	@DatabaseField
	private String id_punto;
	
	/**
	 * Descripcion de esta imagen en concreto
	 */
	@DatabaseField
	private String descripcion;
	
	
	/**
     * Descripcion de esta imagen en concreto, en inglés
     */
    @DatabaseField
    private String desc_en;
    
	/**
	 * URI dentro de la carpeta externa media/maps/<mapa>/
	 */
	@DatabaseField
	private String	uri;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMapa() {
		return mapa;
	}

	public void setMapa(String mapa) {
		this.mapa = mapa;
	}

	public String getId_punto() {
		return id_punto;
	}

	public void setId_punto(String id_punto) {
		this.id_punto = id_punto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public MapImage(Integer id, String mapa, String id_punto, String descripcion, String uri) {
		super();
		this.id = id;
		this.mapa = mapa;
		this.id_punto = id_punto;
		this.descripcion = descripcion;
		this.uri = uri;
	}

	public MapImage() {
		super();
	}

    public String getDesc_en() {
        return desc_en;
    }

    public void setDesc_en(String desc_en) {
        this.desc_en = desc_en;
    }
    
    
    public String getDescByLocale(Locale locale)
    {
        if (locale.getLanguage().equals("en")) {
            return this.getDesc_en();
        }
        return this.getDescripcion();
    }
	
	
}
