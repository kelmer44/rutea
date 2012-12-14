package com.bretema.rutas.activities;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.mapsforge.android.maps.MapActivity;
import org.mapsforge.android.maps.MapController;
import org.mapsforge.android.maps.MapView;
import org.mapsforge.android.maps.overlay.ArrayItemizedOverlay;
import org.mapsforge.android.maps.overlay.OverlayItem;
import org.mapsforge.core.GeoPoint;
import org.mapsforge.map.reader.header.FileOpenResult;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import com.bretema.rutas.R;
import com.bretema.rutas.model.poi.Poi;
import com.bretema.rutas.model.ruta.Ruta;
import com.bretema.rutas.service.PoiService;
import com.bretema.rutas.service.RutaService;
import com.bretema.rutas.service.impl.PoiServiceImpl;
import com.bretema.rutas.service.impl.RutaServiceImpl;

public class RouteMapActivity extends MapActivity {

	private static final String	LOG_TAG		= RouteMapActivity.class
													.getSimpleName();

	private MapView				mapView;
	private MapController		mapController;
	private Gallery				selectedPOIgallery;
	private Context				mContext;
	private AssetManager		assetManager;

	private String				id_ruta;
	private Ruta				ruta;
	private List<Poi>			simplePoiList;

	// Servicio desde el que abstraemos la base de datos
	private RutaService			rutaService;
	private PoiService			poiService;
	
	//Overlays de pois
	private ArrayItemizedOverlay	itemsOverlay;

	// Static images for the moment
	private String[]			mThumbIds	= { "ruta1/thumb1.jpg",
			"ruta1/thumb2.jpg", "ruta1/thumb3.jpg", "ruta1/thumb4.jpg",
			"ruta1/thumb5.jpg", "ruta1/thumb6.jpg", "ruta1/thumb7.jpg",
			"ruta1/thumb8.jpg", "ruta1/thumb9.jpg", "ruta1/thumb10.jpg" };

	@Override
	protected final void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_map);

		// obtenemos la llamada a esta activity
		Intent i = getIntent();
		// y recogemos el id de la linea que nos han pasado
		id_ruta = i.getStringExtra("id_ruta");

		rutaService = new RutaServiceImpl(getApplicationContext());
		poiService = new PoiServiceImpl(getApplicationContext());

		assetManager = getAssets();

		
		initData();
		initMapData();

		selectedPOIgallery = (Gallery) findViewById(R.id.selectedPOIgallery);
		selectedPOIgallery.setAdapter(new ImageAdapter(this));
	}

	private void initMapData() {
		
		
		mapView = (MapView) findViewById(R.id.mapView);
		mapView.setClickable(true);
		mapView.setBuiltInZoomControls(true);

		mapController = mapView.getController();

		File mapFile = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/maps/galicia.map");

		Log.d(LOG_TAG, "Trying to load file" + mapFile.getName());

		FileOpenResult fileOpenResult = mapView.setMapFile(mapFile);
		if (!fileOpenResult.isSuccess()) {
			Toast.makeText(this, fileOpenResult.getErrorMessage(), Toast.LENGTH_LONG).show();
			Log.d(LOG_TAG, "Map file could not be loaded");
			finish();
		} else {
			Log.d(LOG_TAG, "Map file loaded successfully");
		}
		

		Drawable marker = getResources().getDrawable(R.drawable.mapmarker);
		
		
		itemsOverlay = new ArrayItemizedOverlay(marker);
		
		Log.d(LOG_TAG, "Loading overlay items into map");
		for(Poi p: simplePoiList){
			OverlayItem overlay = new OverlayItem(new GeoPoint(p.getLatitude(), p.getLongitude()), p.getNombre(), p.getDescripcion());
			itemsOverlay.addItem(overlay);
		}
		
		mapView.getOverlays().add(itemsOverlay);
	}

	private void initData() {
		
		ruta = rutaService.getRuta(Integer.parseInt(id_ruta));
		Log.d(LOG_TAG, "Retrieving POI list for ruta " + ruta.getId());
		//simplePoiList = poiService.findAll(); 
		simplePoiList = poiService.getSimplePoiByRuta(ruta.getId());
		
		for(Poi p: simplePoiList){
			Log.d(LOG_TAG, p.getNombre() + " Lat,Lon: " + p.getLatitude() + ", " + p.getLongitude());
		}

	}

	public class ImageAdapter extends BaseAdapter {
		public ImageAdapter(final Context c) {
			mContext = c;
		}

		public final int getCount() {
			return mThumbIds.length;
		}

		public final Object getItem(final int position) {
			return position;
		}

		public final long getItemId(final int position) {
			return position;
		}

		public final View getView(final int position, final View convertView, final ViewGroup parent) {
			ImageView i = new ImageView(mContext);

			try {
				i.setImageBitmap(BitmapFactory.decodeStream(assetManager
						.open(mThumbIds[position])));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.d(LOG_TAG, "No se pudo abrir el recurso"
						+ mThumbIds[position]);
			}
			i.setAdjustViewBounds(true);
			// i.setLayoutParams(new
			// Gallery.LayoutParams(LayoutParams.WRAP_CONTENT,
			// LayoutParams.WRAP_CONTENT));
			// i.setBackgroundResource(R.drawable.picture_frame);
			return i;
		}

	}

}
