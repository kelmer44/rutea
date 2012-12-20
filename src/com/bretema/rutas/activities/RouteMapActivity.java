package com.bretema.rutas.activities;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.mapsforge.android.maps.MapActivity;
import org.mapsforge.android.maps.MapController;
import org.mapsforge.android.maps.MapScaleBar;
import org.mapsforge.android.maps.MapView;
import org.mapsforge.android.maps.overlay.ArrayWayOverlay;
import org.mapsforge.android.maps.overlay.OverlayItem;
import org.mapsforge.android.maps.overlay.OverlayWay;
import org.mapsforge.core.GeoPoint;
import org.mapsforge.map.reader.header.FileOpenResult;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bretema.rutas.R;
import com.bretema.rutas.core.util.Constants;
import com.bretema.rutas.enums.PoiType;
import com.bretema.rutas.map.OverlayForge;
import com.bretema.rutas.model.media.Multimedia;
import com.bretema.rutas.model.poi.Poi;
import com.bretema.rutas.model.ruta.Ruta;
import com.bretema.rutas.service.PoiService;
import com.bretema.rutas.service.RutaService;
import com.bretema.rutas.service.impl.PoiServiceImpl;
import com.bretema.rutas.service.impl.RutaServiceImpl;
import com.bretema.rutas.view.ImageAdapter;

public class RouteMapActivity extends MapActivity implements OnClickListener {

	private static final String	LOG_TAG				= RouteMapActivity.class.getSimpleName();
	// Progress Dialog
	private ProgressDialog		pDialog;
	// UI elements
	private Button				nextPoiButton, prevPoiButton, quitRouteButton, buttonBackToRoute;
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
	private List<Poi>			secondaryPoiList;
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

	// Images to show in gallery
	private List<String>		mThumbList;

	private Drawable			marker_red, marker, me_drawable;

	private ViewFlipper			vf;
	private Animation			animFlipInNext, animFlipOutNext, animFlipInPrevious, animFlipOutPrevious;

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

		gotoRouteButton = (ImageButton) findViewById(R.id.gotoRouteButton);
		mapView = (MapView) findViewById(R.id.mapView);
		mapView.setClickable(true);
		mapView.setBuiltInZoomControls(true);
		mapView.getMapZoomControls().setZoomControlsGravity(Gravity.TOP | Gravity.RIGHT);
		mapController = mapView.getController();

		RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.RelativeLayout1);

		linearLayoutLeftPanel = (RelativeLayout) findViewById(R.id.leftMenuBarRoute);
		buttonHideGallery = (ImageButton) findViewById(R.id.buttonHideGallery);
		nextPoiButton = (Button) findViewById(R.id.buttonNextPoi);
		prevPoiButton = (Button) findViewById(R.id.buttonPrevPoi);
		quitRouteButton = (Button) findViewById(R.id.buttonQuitRoute);
		buttonBackToRoute = (Button) findViewById(R.id.buttonBackToRoute);

		vf = (ViewFlipper) findViewById(R.id.ViewFlipper01);
		animFlipInNext = AnimationUtils.loadAnimation(this, R.anim.flipinnext);
		animFlipOutNext = AnimationUtils.loadAnimation(this, R.anim.flipoutnext);
		animFlipInPrevious = AnimationUtils.loadAnimation(this, R.anim.flipinprevious);
		animFlipOutPrevious = AnimationUtils.loadAnimation(this, R.anim.flipoutprevious);

		linearLayoutLeftPanel.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// DO NOTHING, this is just to intercept touch events and
				// prevent balloons from disappearing
				return true;
			}
		});

		buttonHideGallery.setOnClickListener(this);
		gotoRouteButton.setOnClickListener(this);
		nextPoiButton.setOnClickListener(this);
		prevPoiButton.setOnClickListener(this);
		quitRouteButton.setOnClickListener(this);
		buttonBackToRoute.setOnClickListener(this);

		rutaService = new RutaServiceImpl(getApplicationContext());
		poiService = new PoiServiceImpl(getApplicationContext());

		initData();

		// If everything went fine...
		if (initMapData()) {
			new RouteLoader().execute(ruta.getRouteFile());
		}
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

	private void loadPoiOverlays(List<Poi> lista) {
		for (Poi p : lista) {
			OverlayItem overlay = new OverlayItem(new GeoPoint(p.getLatitude(), p.getLongitude()), p.getNombre(), p.getDescripcion());
			//Drawable d = getResources().getDrawable(p.getTipo().getDrawable());
			//overlay.setMarker(d);
			if(p.getTipo()==PoiType.FarmaciaPoi)
				itemsOverlay.addOverlay(overlay, getResources().getDrawable(R.drawable.red_cross));
			else
				itemsOverlay.addOverlay(overlay);
		}
	}

	private void overlayRoute() {
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

		mapView.getOverlays().add(arrayWayOverlay);
		mapView.getOverlays().add(itemsOverlay);
		mapView.invalidate();

	}

	private boolean initMapData() {
		File mapFile = new File(Environment.getExternalStorageDirectory().getPath() + "/maps/galicia.map");

		Log.d(LOG_TAG, "Trying to load file" + mapFile.getName());

		FileOpenResult fileOpenResult = mapView.setMapFile(mapFile);
		if (!fileOpenResult.isSuccess()) {
			Toast.makeText(this, fileOpenResult.getErrorMessage(), Toast.LENGTH_LONG).show();
			RouteMapActivity.this.finish();
			Log.d(LOG_TAG, "Map file could not be loaded");
			return false;
		} else {
			Log.d(LOG_TAG, "Map file loaded successfully");
			marker = getResources().getDrawable(R.drawable.marker_green);
			marker_red = getResources().getDrawable(R.drawable.marker_red);

			me_drawable = getResources().getDrawable(R.drawable.ic_maps_indicator_current_position_anim1);

			itemsOverlay = new OverlayForge(marker, marker_red, me_drawable, mapView, this);
			itemsOverlay.enableMyLocation();
			itemsOverlay.setBalloonBottomOffset(100);
			Log.d(LOG_TAG, "Loading overlay items into map");
			loadPoiOverlays(simplePoiList);
			return true;
		}

	}

	private void initData() {
		mThumbList = new ArrayList<String>();
		ruta = rutaService.getRuta(Integer.parseInt(id_ruta));
		Log.d(LOG_TAG, "Retrieving POI list for ruta " + ruta.getId());
		// simplePoiList = poiService.findAll();
		// simplePoiList = poiService.findAll();
		simplePoiList = poiService.getSimplePoiOrderedByRuta(ruta.getId());
		secondaryPoiList = poiService.getOtherPoiOrderedByRuta(ruta.getId());
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

		for (Poi p : secondaryPoiList) {
			Log.d(LOG_TAG, p.getNombre() + " Lat,Lon: " + p.getLatitude() + ", " + p.getLongitude() + "; orden " + p.getOrden());
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
			itemsOverlay.selectPOIOverlay(index);
			getImagesFromSelectedPoi();

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

	private class RouteLoader extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
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
				double lat = -1;
				double lon = -1;

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
				return false;
			} catch (SecurityException se) {
				Log.d(LOG_TAG, "Error de seguridad: " + se.getMessage());
				RouteMapActivity.this.finish();
				return false;
			} catch (RuntimeException re) {
				Log.d(LOG_TAG, "Error Runtime: " + re.getMessage());
				RouteMapActivity.this.finish();
				return false;
			} catch (Exception e) {
				Log.d("RouteLoader", "Failed in parsing XML", e);
				RouteMapActivity.this.finish();
				return false;
			}

			return true;
		}

		protected void onCancelled() {
			Log.i("RouteLoader", "GetRoute task Cancelled");
		}

		// Now that route data are loaded, execute the method to overlay the
		// route on the map
		protected void onPostExecute(Boolean result) {

			// dismiss the dialog after processing
			pDialog.dismiss();
			if (result) {
				// updating UI from Background Thread
				runOnUiThread(new Runnable() {
					public void run() {

						Log.i(LOG_TAG, "Route data transfer complete");
						overlayRoute();

					}
				});
			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(RouteMapActivity.this);
				builder.setMessage(getResources().getString(R.string.errorfetching));
				builder.setCancelable(false);
				builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						RouteMapActivity.this.finish();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
		}

		protected void onPreExecute() {
			Log.i(LOG_TAG, "Ready to load URL");
			pDialog = new ProgressDialog(RouteMapActivity.this);
			pDialog.setMessage(getResources().getString(R.string.pleasewait));
			pDialog.setIndeterminate(false);
			pDialog.setTitle(getResources().getString(R.string.fetchingdata));
			pDialog.setCancelable(true);
			pDialog.show();

			pDialog.setOnCancelListener(new OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					RouteLoader.this.cancel(true);
				}
			});
		}

		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.mapView.destroyDrawingCache();
		this.mapView.removeAllViews();
		this.mapView.getOverlays().clear();
		this.mapView = null;
		this.itemsOverlay = null;
		this.arrayWayOverlay = null;
	}

	@Override
	public void onClick(View v) {

		if (v == buttonHideGallery) {
			if (galleryHidden) {
				showGallery();

			} else {
				hideGallery();
			}

		}

		else if (v == gotoRouteButton) {
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

		else if (v == nextPoiButton) {
			selectNextPoi();
		}

		else if (v == prevPoiButton) {
			selectPreviousPoi();
		}

		else if (v == quitRouteButton) {
			vf.setInAnimation(animFlipInNext);
			vf.setOutAnimation(animFlipOutPrevious);
			itemsOverlay.removeAllPois();
			loadPoiOverlays(secondaryPoiList);
			vf.showNext();
		} else if (v == buttonBackToRoute) {
			vf.setInAnimation(animFlipInNext);
			vf.setOutAnimation(animFlipOutPrevious);
			itemsOverlay.removeAllPois();
			loadPoiOverlays(simplePoiList);
			vf.showPrevious();
		}
	}
}
