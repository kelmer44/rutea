
package com.bretema.rutas.model.ruta;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Locale;

/**
 * Define una instancia de rutas
 * 
 * @author Gabriel Sanmart�n D�az
 */

@DatabaseTable
public class Ruta {

    @DatabaseField(generatedId = true)
    private Integer id;

    /**
     * Nombre de la ruta
     */
    @DatabaseField
    private String nombre;

    /**
     * Nombre de la ruta en inglés
     */
    @DatabaseField
    private String nombre_en;

    /**
     * Descripci�n de la ruta (introducci�n)
     */
    @DatabaseField
    private String description;

    /**
     * Descripción de la ruta (introducción) en inglés
     */
    @DatabaseField
    private String desc_en;

    /**
     * Imagen principal
     */
    @DatabaseField
    private String mainImagePath;

    /**
     * Descripci�n corta (en desuso?)
     */
    @DatabaseField
    private String shortDescription;

    /**
     * URI del archivo GPX
     */
    @DatabaseField
    private String routeFile;

    /**
     * imagen de muestreo en la pantalla principal
     */
    @DatabaseField
    private String listImagePath;

    /**
     * Archivo de audio con la introducci�n de la ruta
     */
    @DatabaseField
    private String introAudioPath;

    /**
     * Duracion temporal de la ruta
     */
    @DatabaseField
    private String duracion;

    @DatabaseField
    private String balloonImagePath;
    /**
     * km de longitud de la ruta
     */
    @DatabaseField
    private float km;

    public Ruta() {
        super();
    }

    public Ruta(String nombre, String description, String mainImagePath, String nombre_en, String desc_en) {
        super();
        this.nombre = nombre;
        this.description = description;
        this.mainImagePath = mainImagePath;
        this.nombre_en = nombre_en;
        this.desc_en = desc_en;
    }

    public String getListImagePath() {
        return listImagePath;
    }

    public void setListImagePath(String listImagePath) {
        this.listImagePath = listImagePath;
    }

    public String getIntroAudioPath() {
        return introAudioPath;
    }

    public void setIntroAudioPath(String introAudioPath) {
        this.introAudioPath = introAudioPath;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public float getKm() {
        return km;
    }

    public void setKm(float km) {
        this.km = km;
    }

    public String getMainImagePath() {
        return mainImagePath;
    }

    public void setMainImagePath(String mainImagePath) {
        this.mainImagePath = mainImagePath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getRouteFile() {
        return routeFile;
    }

    public void setRouteFile(String routeFile) {
        this.routeFile = routeFile;
    }

    public Ruta(String nombre, String description, String mainImagePath, String shortDescription, String routeFile, String listImagePath, String introAudioPath, String duracion, float km, String nombre_en, String desc_en) {
        super();
        this.nombre = nombre;
        this.description = description;
        this.mainImagePath = mainImagePath;
        this.shortDescription = shortDescription;
        this.routeFile = routeFile;
        this.listImagePath = listImagePath;
        this.introAudioPath = introAudioPath;
        this.duracion = duracion;
        this.km = km;
        this.nombre_en = nombre_en;
        this.desc_en = desc_en;
                
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
        return this.getDescription();
    }

}
