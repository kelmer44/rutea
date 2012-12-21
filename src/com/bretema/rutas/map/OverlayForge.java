package com.bretema.rutas.map;

import java.util.ArrayList;
import java.util.List;

import org.mapsforge.android.maps.MapController;
import org.mapsforge.android.maps.MapView;
import org.mapsforge.android.maps.overlay.ItemizedOverlay;
import org.mapsforge.android.maps.overlay.Overlay;
import org.mapsforge.android.maps.overlay.OverlayItem;
import org.mapsforge.core.GeoPoint;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bretema.rutas.R;
import com.bretema.rutas.activities.RouteMapActivity;

public class OverlayForge extends ItemizedOverlay<OverlayItem>{

	private RouteMapActivity			routeActivity;
	private Context						context;

	private BalloonOverlay<OverlayItem>	balloonView;
	private LinearLayout				balloonLayout;
	private TextView					balloonTitle;
	private TextView					balloonSnippet;
	private View						balloonClickRegion;
	private int							balloonOffset;

	private MapView						mapView;
	private Drawable					defaultMarker;
	private Drawable					selectMarker;
	private Drawable					myLocationMarker;

	private ArrayList<OverlayItem>		m_overlays			= new ArrayList<OverlayItem>();
	private ArrayList<OverlayItem>		fullList			= new ArrayList<OverlayItem>();
	private OverlayItem					me_overlay			= new OverlayItem();
	private OverlayItem					currentFocusedItem;
	private int							currentFocusedIndex;

	private Location					lastKnownLocation	= null;
	private LocationManager	mgr;
	private String	bestProvider;

	public OverlayForge(Drawable defaultMarker, Drawable selectedMarker, Drawable myLocationMarker, MapView mapView, RouteMapActivity activity) {
		super(boundCenterBottom(defaultMarker));
		// Updating the markers
		this.defaultMarker = boundCenterBottom(defaultMarker);
		this.selectMarker = boundCenterBottom(selectedMarker);
		this.myLocationMarker = boundCenter(myLocationMarker);
		
		this.routeActivity = activity;
		this.context = mapView.getContext();
		this.mapView = mapView;

		this.me_overlay.setMarker(myLocationMarker);
		this.fullList.add(me_overlay);
		
		this.balloonLayout = new LinearLayout(context);
		this.balloonLayout.setVisibility(View.VISIBLE);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View balloonView = inflater.inflate(R.layout.balloon_overlay, balloonLayout);

		this.balloonTitle = (TextView) balloonView.findViewById(R.id.balloon_item_title);
		this.balloonSnippet = (TextView) balloonView.findViewById(R.id.balloon_item_snippet);

		// This is done only to eliminate layot
		this.mapView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (balloonLayout != null)
					balloonLayout.setVisibility(LinearLayout.GONE);
				return false;
			}
		});
		

		mgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		bestProvider = mgr.getBestProvider(new Criteria(), true);
		if (bestProvider != null && !bestProvider.equals("")) {
			//mgr.requestLocationUpdates(bestProvider, 0, 0, onLocationChange);
		}
	}

	/**
	 * selecciona el POI indicado, poniendo el marcador rojo, centrandoe l mapa y mostrando el bocadillo
	 * @param idx DENTRO DEL ARRAY DE POIS, no incluyendo el marcador de localizacion del usuario
	 */
	public void selectPOIOverlay(int idx) {
		for (int i = 0; i < m_overlays.size(); i++) {
			//Ponemos el marcador verde al resto
			m_overlays.get(i).setMarker(defaultMarker);
		}
		OverlayItem oi = m_overlays.get(idx);
		//Y el rojo al seleccionado
		m_overlays.get(idx).setMarker(selectMarker);
		mapView.getController().setCenter(oi.getPoint());
		
		doShowBallon(idx);
		
		mapView.invalidate();
	}

	public void addOverlay(OverlayItem overlay) {

		m_overlays.add(overlay);
		fullList.add(overlay);
		populate();
	}
	
	public void addOverlay(OverlayItem overlay, Drawable d) {
		overlay.setMarker(boundCenter(context.getApplicationContext().getResources().getDrawable(R.drawable.red_cross)));
		m_overlays.add(overlay);
		fullList.add(overlay);
		populate();
	}


	@Override
	protected OverlayItem createItem(int i) {
		return fullList.get(i);
	}

	@Override
	public int size() {
		return fullList.size();
	}

	public void doShowBallon(int index) {
		currentFocusedIndex = index;
		currentFocusedItem = m_overlays.get(index);
		
		((ViewGroup) mapView.getParent()).removeView(balloonLayout);

		MapController mMapController = mapView.getController();
		mMapController.setCenter(currentFocusedItem.getPoint());

		balloonLayout.setVisibility(View.VISIBLE);
		if (currentFocusedItem.getTitle() != null) {
			balloonTitle.setVisibility(View.VISIBLE);
			balloonTitle.setText(currentFocusedItem.getTitle());
		} else {
			balloonTitle.setVisibility(View.GONE);
		}

		if (currentFocusedItem.getSnippet() != null) {
			balloonSnippet.setVisibility(View.VISIBLE);
			balloonSnippet.setText(currentFocusedItem.getSnippet());
		} else {
			balloonSnippet.setVisibility(View.GONE);
		}

		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;

		balloonLayout.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		params.topMargin -= balloonLayout.getMeasuredHeight() - (balloonLayout.getMeasuredHeight() / 2) + balloonOffset;

		ImageView imgClose = (ImageView) balloonLayout.findViewById(R.id.close_img_button);
		imgClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				balloonLayout.setVisibility(View.GONE);
				((ViewGroup) mapView.getParent()).removeView(balloonLayout);
			}

		});

		balloonClickRegion = balloonLayout.findViewById(R.id.balloon_inner_layout);
		balloonClickRegion.setOnTouchListener(createBalloonTouchListener());

		((ViewGroup) mapView.getParent()).addView(balloonLayout, params);
	}

	@Override
	protected final boolean onTap(int index) {
		if(index == fullList.indexOf(me_overlay))
			return true;
		doShowBallon(index-1);
		routeActivity.selectPoi(index-1);
		return true;
	}

	protected boolean onBalloonTap(int index, OverlayItem item) {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + item.getPoint().getLatitude() + ","
				+ item.getPoint().getLongitude()));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
		return true;
	}

	private OnTouchListener createBalloonTouchListener() {
		return new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {

				View l = ((View) v.getParent()).findViewById(R.id.balloon_main_layout);
				Drawable d = l.getBackground();

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					int[] states = { android.R.attr.state_pressed };
					if (d.setState(states)) {
						d.invalidateSelf();
					}
					return true;
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					int newStates[] = {};
					if (d.setState(newStates)) {
						d.invalidateSelf();
					}
					// call overridden method
					onBalloonTap(currentFocusedIndex, currentFocusedItem);
					return true;
				} else {
					return false;
				}

			}
		};
	}

	protected MapView getMapView() {
		return mapView;
	}

	public void setBalloonBottomOffset(int pixels) {
		balloonOffset = pixels;
	}

	public int getBalloonBottomOffset() {
		return balloonOffset;
	}

	public void onLocationChanged(Location location) {

	}
	public void disableMyLocation() {
		mgr.removeUpdates(onLocationChange);
	}

	protected boolean dispatchTap() {
		return false;
	}

	public GeoPoint getMyLocation() {
		if (lastKnownLocation != null) {
			return new GeoPoint(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
		} else {
			return null;
		}
	}

	public void removeAllPois() {
		balloonLayout.setVisibility(LinearLayout.GONE);
		fullList.removeAll(m_overlays);
		m_overlays.clear();
		populate();
	}
	
	LocationListener onLocationChange=new LocationListener() {
	    public void onLocationChanged(Location location) {
			lastKnownLocation = location;
			me_overlay.setPoint(getMyLocation());
			OverlayForge.this.requestRedraw();
	    }
	    
	    public void onProviderDisabled(String provider) {
	      // required for interface, not used
	    }
	    
	    public void onProviderEnabled(String provider) {
	      // required for interface, not used
	    }
	    
	    public void onStatusChanged(String provider, int status,
	                                  Bundle extras) {
	      // required for interface, not used
	    }
	  };
}