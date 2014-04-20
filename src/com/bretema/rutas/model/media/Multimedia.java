
package com.bretema.rutas.model.media;

import com.bretema.rutas.R;
import com.bretema.rutas.enums.MMType;
import com.bretema.rutas.model.poi.Poi;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Locale;

/**
 * Define archivos multimedia, tanto im�genes como v�deos, asociados a la rutas
 * 
 * @author Gabriel Sanmartín Díaz
 */
@DatabaseTable
public class Multimedia {
    /**
     * Identificador único
     */
    @DatabaseField(generatedId = true)
    private Integer id;

    /**
     * URI dentro de la carpeta assets (solución temporal) TODO: fix this shit
     */
    @DatabaseField
    private String uri;

    /**
     * URI al thumb
     */
    @DatabaseField
    private String thumbUri;

    /**
     * Nombre del recurso multimedia
     */
    @DatabaseField
    private String nombre;

    /**
     * Nombre del recurso multimedia
     */
    @DatabaseField
    private String nombre_en;

    /**
     * Peque�a descripción de la foto o v�deo
     */
    @DatabaseField
    private String descripcion;

    /**
     * Peque�a descripción de la foto o v�deo
     */
    @DatabaseField
    private String desc_en;

    /**
     * Tipo de recurso según el enum MMtype (Imagen, Vídeo, etc.)
     */
    @DatabaseField(dataType = DataType.ENUM_STRING)
    private MMType tipo;

    /**
     * POI asociado (a través de él deberíamos acceder a la ruta)
     */
    @DatabaseField(foreign = true, columnName = "poiId")
    private Poi poi;

    /**
     * Orden de muestreo (en caso de que �ste sea necesario)
     */
    @DatabaseField
    private int orden;

    /**
     * Más información / informkación adicional
     * 
     * @return
     */
    @DatabaseField
    private String mas_info;
    
    /**
     * Más información / informkación adicional
     * 
     * @return
     */
    @DatabaseField
    private String mas_info_en;

    /**
     * De haber vídeo/imagen o lo que sea iría aquí la ruta
     */
    @DatabaseField
    private String media_masinfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public MMType getTipo() {
        return tipo;
    }

    public void setTipo(MMType tipo) {
        this.tipo = tipo;
    }

    public Poi getPoi() {
        return poi;
    }

    public void setPoi(Poi poi) {
        this.poi = poi;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public Multimedia() {
        super();
    }

    public Multimedia(String uri, String nombre, String descripcion, MMType tipo, Poi poi, int orden) {
        super();
        this.uri = uri;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.poi = poi;
        this.orden = orden;
    }

    public String getThumbUri() {
        return thumbUri;
    }

    public void setThumbUri(String thumbUri) {
        this.thumbUri = thumbUri;
    }

    public String getMas_info() {
        return mas_info;
    }

    public void setMas_info(String mas_info) {
        this.mas_info = mas_info;
    }

    public String getMedia_masinfo() {
        return media_masinfo;
    }

    public void setMedia_masinfo(String media_masinfo) {
        this.media_masinfo = media_masinfo;
    }

    public String getNombre_en() {
        return nombre_en;
    }

    public void setNombre_en(String nombre_en) {
        this.nombre_en = nombre_en;
    }

    public String getDesc_en() {
        return desc_en;
    }

    public void setDesc_en(String desc_en) {
        this.desc_en = desc_en;
    }

    public String getMas_info_en() {
        return mas_info_en;
    }

    public void setMas_info_en(String mas_info_en) {
        this.mas_info_en = mas_info_en;
    }

    
    public String getNombreByLocale(Locale locale)
    {
        if (locale.getLanguage().equals("en")) {
            return this.getNombre_en();
        }
        return this.getNombre();
    }
    
    public String getDescByLocale(Locale locale)
    {
        if (locale.getLanguage().equals("en")) {
            return this.getDesc_en();
        }
        return this.getDescripcion();
    }
    
    
    public String getMasInfoByLocale(Locale locale)
    {
        if (locale.getLanguage().equals("en")) {
            return this.getMas_info_en();
        }
        return this.getMas_info();
    }
    
}
