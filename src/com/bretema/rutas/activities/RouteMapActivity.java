package com.bretema.rutas.activities;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.mapsforge.android.maps.MapActivity;
import org.mapsforge.android.maps.MapController;
import org.mapsforge.android.maps.MapView;
import org.mapsforge.android.maps.overlay.ArrayItemizedOverlay;
import org.mapsforge.android.maps.overlay.ArrayWayOverlay;
import org.mapsforge.android.maps.overlay.OverlayItem;
import org.mapsforge.android.maps.overlay.OverlayWay;
import org.mapsforge.core.GeoPoint;
import org.mapsforge.map.reader.header.FileOpenResult;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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
import com.bretema.rutas.core.util.Constants;
import com.bretema.rutas.model.media.Multimedia;
import com.bretema.rutas.model.poi.Poi;
import com.bretema.rutas.model.ruta.Ruta;
import com.bretema.rutas.service.PoiService;
import com.bretema.rutas.service.RutaService;
import com.bretema.rutas.service.impl.PoiServiceImpl;
import com.bretema.rutas.service.impl.RutaServiceImpl;

public class RouteMapActivity extends MapActivity {

	private static final String		LOG_TAG				= RouteMapActivity.class
																.getSimpleName();

	private MapView					mapView;
	private MapController			mapController;
	private Gallery					selectedPOIgallery;
	private Context					mContext;
	private AssetManager			assetManager;

	private String					id_ruta;
	// Route object
	private Ruta					ruta;
	// simple poi list
	private List<Poi>				simplePoiList;

	// GEoPoint defining the painted route
	int								numberRoutePoints;
	List<GeoPoint>					routePoints;
	boolean							routeIsDisplayed	= false;

	// Servicio desde el que abstraemos la base de datos
	private RutaService				rutaService;
	private PoiService				poiService;

	// Overlays de pois
	private ArrayItemizedOverlay	itemsOverlay;
	private ArrayWayOverlay			arrayWayOverlay;

	// Static images for the moment
	private List<String> mThumbList;

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
		new RouteLoader().execute(ruta.getRouteFile());

		selectedPOIgallery = (Gallery) findViewById(R.id.selectedPOIgallery);
		selectedPOIgallery.setAdapter(new ImageAdapter(this));
	}

	public void overlayRoute() {
		Paint wayDefaultPaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
		wayDefaultPaintFill.setStyle(Paint.Style.STROKE);
		wayDefaultPaintFill.setColor(Color.BLUE);
		wayDefaultPaintFill.setAlpha(160);
		wayDefaultPaintFill.setStrokeWidth(7);
		wayDefaultPaintFill.setStrokeJoin(Paint.Join.ROUND);
		wayDefaultPaintFill.setPathEffect(new DashPathEffect(new float[] { 20,
				20 }, 0));

		Paint wayDefaultPaintOutline = new Paint(Paint.ANTI_ALIAS_FLAG);
		wayDefaultPaintOutline.setStyle(Paint.Style.STROKE);
		wayDefaultPaintOutline.setColor(Color.BLUE);
		wayDefaultPaintOutline.setAlpha(128);
		wayDefaultPaintOutline.setStrokeWidth(7);
		wayDefaultPaintOutline.setStrokeJoin(Paint.Join.ROUND);

		arrayWayOverlay = new ArrayWayOverlay(wayDefaultPaintFill, wayDefaultPaintOutline);
		OverlayWay way = new OverlayWay(Constants.toGeoPointArray(routePoints));
		
		arrayWayOverlay.addWay(way);
		mapView.getOverlays().add(arrayWayOverlay);
		mapView.getOverlays().add(itemsOverlay);
		mapView.invalidate();

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
			Toast.makeText(this, fileOpenResult.getErrorMessage(),
					Toast.LENGTH_LONG).show();
			Log.d(LOG_TAG, "Map file could not be loaded");
			finish();
		} else {
			Log.d(LOG_TAG, "Map file loaded successfully");
		}

		Drawable marker = getResources().getDrawable(R.drawable.marker_green);

		itemsOverlay = new ArrayItemizedOverlay(marker);

		Log.d(LOG_TAG, "Loading overlay items into map");
		for (Poi p : simplePoiList) {
			OverlayItem overlay = new OverlayItem(new GeoPoint(p.getLatitude(), p
					.getLongitude()), p.getNombre(), p.getDescripcion());
			itemsOverlay.addItem(overlay);
		}

	}

	private void initData() {
		mThumbList = new ArrayList<String>();
		ruta = rutaService.getRuta(Integer.parseInt(id_ruta));
		Log.d(LOG_TAG, "Retrieving POI list for ruta " + ruta.getId());
		// simplePoiList = poiService.findAll();
		//simplePoiList = poiService.findAll();
		simplePoiList = poiService.getSimplePoiOrderedByRuta(ruta.getId());

		for (Poi p : simplePoiList) {
			Log.d(LOG_TAG, p.getNombre() + " Lat,Lon: " + p.getLatitude()
					+ ", " + p.getLongitude() + "; orden " + p.getOrden());
			for(Multimedia mm:p.getMedia()){
				mThumbList.add(mm.getThumbUri());
			}
		}

	}

	public class ImageAdapter extends BaseAdapter {
		public ImageAdapter(final Context c) {
			mContext = c;
		}

		public final int getCount() {
			return mThumbList.size();
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
						.open(mThumbList.get(position))));
			} catch (IOException e) {
				Log.d(LOG_TAG, "No se pudo abrir el recurso"
						+ mThumbList.get(position));
			}
			i.setAdjustViewBounds(true);
			// i.setLayoutParams(new
			// Gallery.LayoutParams(LayoutParams.WRAP_CONTENT,
			// LayoutParams.WRAP_CONTENT));
			// i.setBackgroundResource(R.drawable.picture_frame);
			return i;
		}

	}

	private class RouteLoader extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			// This pattern takes more than one param but we'll just use the
			// first
			try {
				String filePath = params[0];

				XmlPullParserFactory parserCreator;

				parserCreator = XmlPullParserFactory.newInstance();

				XmlPullParser parser = parserCreator.newPullParser();

				InputStream is = getAssets().open(filePath);
				parser.setInput(new InputStreamReader(is));

				// publishProgress("Parsing XML...");

				int parserEvent = parser.getEventType();
				int pointCounter = -1;
				int wptCounter = -1;
				int totalWaypoints = -1;
				double lat = -1;
				double lon = -1;
				String wptDescription = "";
				int grade = -1;

				routePoints = new ArrayList<GeoPoint>();
				// Parse the XML returned on the network
				while (parserEvent != XmlPullParser.END_DOCUMENT) {
					switch (parserEvent) {
						case XmlPullParser.START_TAG:
							String tag = parser.getName();
							if (tag.compareTo("trkpt") == 0) {
								pointCounter++;
								lat = Double.parseDouble(parser
										.getAttributeValue(null, "lat"));
								lon = Double.parseDouble(parser
										.getAttributeValue(null, "lon"));
								routePoints.add(new GeoPoint(lat, lon));
								//Log.i(LOG_TAG, "   trackpoint=" + pointCounter
										//+ " latitude=" + lat + " longitude="
										//+ lon);
							}
							break;
					}

					parserEvent = parser.next();
				}
				numberRoutePoints = routePoints.size();

			} catch (Exception e) {
				Log.d("RouteLoader", "Failed in parsing XML", e);
				return "Finished with failure.";
			}

			return "Done...";
		}

		protected void onCancelled() {
			Log.i("RouteLoader", "GetRoute task Cancelled");
		}

		// Now that route data are loaded, execute the method to overlay the
		// route on the map
		protected void onPostExecute(String result) {
			Log.i(LOG_TAG, "Route data transfer complete");
			overlayRoute();
		}

		protected void onPreExecute() {
			Log.i(LOG_TAG, "Ready to load URL");
		}

		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
		}

	}
}
