package com.bretema.rutas.activities;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.mapsforge.android.maps.MapActivity;
import org.mapsforge.android.maps.MapController;
import org.mapsforge.android.maps.MapView;
import org.mapsforge.android.maps.Projection;
import org.mapsforge.android.maps.overlay.ArrayWayOverlay;
import org.mapsforge.android.maps.overlay.OverlayItem;
import org.mapsforge.android.maps.overlay.OverlayWay;
import org.mapsforge.core.GeoPoint;
import org.mapsforge.map.reader.header.FileOpenResult;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bretema.rutas.R;
import com.bretema.rutas.core.util.Constants;
import com.bretema.rutas.map.MyLocationOverlay;
import com.bretema.rutas.map.OverlayForge;
import com.bretema.rutas.model.media.Multimedia;
import com.bretema.rutas.model.poi.Poi;
import com.bretema.rutas.model.ruta.Ruta;
import com.bretema.rutas.service.PoiService;
import com.bretema.rutas.service.RutaService;
import com.bretema.rutas.service.impl.PoiServiceImpl;
import com.bretema.rutas.service.impl.RutaServiceImpl;
import com.bretema.rutas.view.ImageAdapter;

public class RouteMapActivity extends MapActivity {

	private static final String	LOG_TAG				= RouteMapActivity.class.getSimpleName();

	// UI elements
	private Button				nextPoiButton;
	private Button				prevPoiButton;
	private ImageButton			gotoRouteButton;
	private MapView				mapView;
	private MapController		mapController;
	private Gallery				selectedPOIgallery;
	private ImageButton			buttonHideGallery;
	private RelativeLayout		linearLayoutLeftPanel;
	private boolean				galleryHidden;

	private String				id_ruta;
	// Route object
	private Ruta				ruta;
	// simple poi list
	private List<Poi>			simplePoiList;
	private Poi					selectedPoi;

	// GEoPoint defining the painted route
	int							numberRoutePoints;
	List<GeoPoint>				routePoints;
	boolean						routeIsDisplayed	= false;

	// Servicio desde el que abstraemos la base de datos
	private RutaService			rutaService;
	private PoiService			poiService;

	// Overlays de pois
	private OverlayForge		itemsOverlay;
	// Overlay de ruta
	private ArrayWayOverlay		arrayWayOverlay;
	// Overlay con mi posicion
	private MyLocationOverlay	myLocationOverlay;

	// Images to show in gallery
	private List<String>		mThumbList;

	private Drawable			marker_red;

	private Drawable			marker;

	@Override
	protected final void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_map);

		// obtenemos la llamada a esta activity
		Intent i = getIntent();
		// y recogemos el id de la linea que nos han pasado
		id_ruta = i.getStringExtra("id_ruta");
		galleryHidden = true;

		linearLayoutLeftPanel = (RelativeLayout) findViewById(R.id.leftMenuBarRoute);
		buttonHideGallery = (ImageButton) findViewById(R.id.buttonHideGallery);
		nextPoiButton = (Button) findViewById(R.id.buttonNextPoi);
		prevPoiButton = (Button) findViewById(R.id.buttonPrevPoi);
		gotoRouteButton = (ImageButton) findViewById(R.id.gotoRouteButton);

		linearLayoutLeftPanel.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// DO NOTHING, this is just to intercept touch events and
				// prevent balloons from disappearing
				return true;
			}
		});

		buttonHideGallery.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (galleryHidden) {
					showGallery();

				} else {
					hideGallery();
				}

			}
		});

		gotoRouteButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Intent intent = new Intent(Intent.ACTION_VIEW,
				// Uri.parse("geo:" + selectedPoi.getLatitude() + "," +
				// selectedPoi.getLongitude() + "?q=" +
				// selectedPoi.getLatitude() + "," + selectedPoi.getLongitude()
				// + "(" + selectedPoi.getNombre() + ")&z=17"));
				// startActivity(intent);
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + selectedPoi.getLatitude() + ","
						+ selectedPoi.getLongitude()));
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});

		nextPoiButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				selectNextPoi();
			}
		});

		prevPoiButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				selectPreviousPoi();
			}
		});

		rutaService = new RutaServiceImpl(getApplicationContext());
		poiService = new PoiServiceImpl(getApplicationContext());

		initData();
		new RouteLoader().execute(ruta.getRouteFile());

		// selectPoi(0);
	}

	protected void showGallery() {
		buttonHideGallery.setImageResource(android.R.drawable.arrow_down_float);
		galleryHidden = false;
		selectedPOIgallery.setVisibility(Gallery.VISIBLE);
		selectedPOIgallery.invalidate();

	}

	protected void hideGallery() {

		buttonHideGallery.setImageResource(android.R.drawable.arrow_up_float);
		galleryHidden = true;
		selectedPOIgallery.setVisibility(Gallery.GONE);
		selectedPOIgallery.invalidate();
	}

	public void overlayRoute() {
		Paint wayDefaultPaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
		wayDefaultPaintFill.setStyle(Paint.Style.STROKE);
		wayDefaultPaintFill.setColor(Color.BLUE);
		wayDefaultPaintFill.setAlpha(160);
		wayDefaultPaintFill.setStrokeWidth(7);
		wayDefaultPaintFill.setStrokeJoin(Paint.Join.ROUND);
		wayDefaultPaintFill.setPathEffect(new DashPathEffect(new float[] { 20, 20 }, 0));

		Paint wayDefaultPaintOutline = new Paint(Paint.ANTI_ALIAS_FLAG);
		wayDefaultPaintOutline.setStyle(Paint.Style.STROKE);
		wayDefaultPaintOutline.setColor(Color.BLUE);
		wayDefaultPaintOutline.setAlpha(128);
		wayDefaultPaintOutline.setStrokeWidth(7);
		wayDefaultPaintOutline.setStrokeJoin(Paint.Join.ROUND);

		arrayWayOverlay = new ArrayWayOverlay(wayDefaultPaintFill, wayDefaultPaintOutline);
		OverlayWay way = new OverlayWay(Constants.toGeoPointArray(routePoints));

		arrayWayOverlay.addWay(way);
		selectPoi(0);
		Drawable me_drawable = getResources().getDrawable(R.drawable.ic_maps_indicator_current_position_anim1);
		myLocationOverlay = new MyLocationOverlay(me_drawable, this, this.mapView);
		myLocationOverlay.enableMyLocation();
		mapView.getOverlays().add(arrayWayOverlay);
		mapView.getOverlays().add(myLocationOverlay);
		mapView.invalidate();

	}

	private void initMapData() {

		mapView = (MapView) findViewById(R.id.mapView);
		mapView.setClickable(true);
		mapView.setBuiltInZoomControls(false);
		mapController = mapView.getController();

		File mapFile = new File(Environment.getExternalStorageDirectory().getPath() + "/maps/galicia.map");

		Log.d(LOG_TAG, "Trying to load file" + mapFile.getName());

		FileOpenResult fileOpenResult = mapView.setMapFile(mapFile);
		if (!fileOpenResult.isSuccess()) {
			Toast.makeText(this, fileOpenResult.getErrorMessage(), Toast.LENGTH_LONG).show();
			Log.d(LOG_TAG, "Map file could not be loaded");
			finish();
		} else {
			Log.d(LOG_TAG, "Map file loaded successfully");
		}

		marker = getResources().getDrawable(R.drawable.marker_green);
		marker_red = getResources().getDrawable(R.drawable.marker_red);

		itemsOverlay = new OverlayForge(marker, marker_red, mapView, this);
		itemsOverlay.setBalloonBottomOffset(100);
		Log.d(LOG_TAG, "Loading overlay items into map");
		for (Poi p : simplePoiList) {
			OverlayItem overlay = new OverlayItem(new GeoPoint(p.getLatitude(), p.getLongitude()), p.getNombre(), p.getDescripcion());
			itemsOverlay.addOverlay(overlay);
		}

	}

	private void initData() {
		mThumbList = new ArrayList<String>();
		ruta = rutaService.getRuta(Integer.parseInt(id_ruta));
		Log.d(LOG_TAG, "Retrieving POI list for ruta " + ruta.getId());
		// simplePoiList = poiService.findAll();
		// simplePoiList = poiService.findAll();
		simplePoiList = poiService.getSimplePoiOrderedByRuta(ruta.getId());

		// We select first element+
		if (simplePoiList.size() != 0) {
			selectedPoi = simplePoiList.get(0);
			for (Poi p : simplePoiList) {
				Log.d(LOG_TAG, p.getNombre() + " Lat,Lon: " + p.getLatitude() + ", " + p.getLongitude() + "; orden " + p.getOrden());

			}
		} else {
			Toast.makeText(getApplicationContext(), "No se encontraron POIs para la ruta seleccionada.", Toast.LENGTH_LONG).show();
			finish();
		}

	}

	private void selectNextPoi() {
		int indexOfCurrentPoi = simplePoiList.indexOf(selectedPoi);
		if (indexOfCurrentPoi < simplePoiList.size() - 1) {
			selectPoi(indexOfCurrentPoi + 1);
		} else
			selectPoi(0);
	}

	private void selectPreviousPoi() {
		int indexOfCurrentPoi = simplePoiList.indexOf(selectedPoi);
		if (indexOfCurrentPoi > 0) {
			selectPoi(indexOfCurrentPoi - 1);
		} else
			selectPoi(simplePoiList.size() - 1);
	}

	public void selectPoi(int index) {
		if (index < simplePoiList.size()) {
			Log.d(LOG_TAG, "Selecting poi " + index);
			selectedPoi = simplePoiList.get(index);
			// mapView.getOverlays().remove(selectedOverlay);
			itemsOverlay.selectOverlay(index);
			GeoPoint selectedPoint = new GeoPoint(selectedPoi.getLatitude(), selectedPoi.getLongitude());
			mapController.setCenter(selectedPoint);
			// mapView.getOverlays().add(selectedOverlay);
			mapView.invalidate();
			getImagesFromSelectedPoi();

			itemsOverlay.doShowBallon(index);
		}
	}
	
	private void getImagesFromSelectedPoi() {
		mThumbList.clear();
		for (Multimedia mm : selectedPoi.getMedia()) {
			mThumbList.add(mm.getThumbUri());
		}
		selectedPOIgallery = (Gallery) findViewById(R.id.selectedPOIgallery);
		selectedPOIgallery.setAdapter(new ImageAdapter(this, mThumbList));
	}

	private class RouteLoader extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			// This pattern takes more than one param but we'll just use the
			// first
			try {

				initMapData();
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
								lat = Double.parseDouble(parser.getAttributeValue(null, "lat"));
								lon = Double.parseDouble(parser.getAttributeValue(null, "lon"));
								routePoints.add(new GeoPoint(lat, lon));
								// Log.i(LOG_TAG, "   trackpoint=" +
								// pointCounter
								// + " latitude=" + lat + " longitude="
								// + lon);
							}
							break;
					}

					parserEvent = parser.next();
				}
				numberRoutePoints = routePoints.size();

			} catch (IllegalArgumentException iae) {
				Log.d(LOG_TAG, "Error Illegal Argument: " + iae.getMessage());
				RouteMapActivity.this.finish();
			} catch (SecurityException se) {
				Log.d(LOG_TAG, "Error de seguridad: " + se.getMessage());
				RouteMapActivity.this.finish();
			} catch (RuntimeException re) {
				Log.d(LOG_TAG, "Error Runtime: " + re.getMessage());
				RouteMapActivity.this.finish();
			} catch (Exception e) {
				Log.d("RouteLoader", "Failed in parsing XML", e);
				RouteMapActivity.this.finish();
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
