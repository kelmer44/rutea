package com.bretema.rutas.map;

import java.util.ArrayList;

import org.mapsforge.android.maps.MapController;
import org.mapsforge.android.maps.MapView;
import org.mapsforge.android.maps.overlay.ItemizedOverlay;
import org.mapsforge.android.maps.overlay.OverlayItem;
import org.mapsforge.core.GeoPoint;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import com.bretema.rutas.activities.SlideShowActivity;
import com.bretema.rutas.core.util.Constants;
import com.bretema.rutas.enums.PoiType;
import com.bretema.rutas.model.poi.Poi;

public class OverlayForge extends ItemizedOverlay<PoiOverlayItem> {

	private RouteMapActivity			routeActivity;
	private Context						context;

	private LinearLayout				balloonLayout;
	private TextView					balloonTitle;
	private TextView					balloonSnippet;
	private ImageView					balloonImage;
	private View						balloonClickRegion;
	private int							balloonOffset;

	private MapView						mapView;
	private Drawable					defaultMarker;
	private Drawable					selectMarker;

	private ArrayList<PoiOverlayItem>	m_overlays	= new ArrayList<PoiOverlayItem>();
	private ArrayList<PoiOverlayItem>	fullList	= new ArrayList<PoiOverlayItem>();
	private PoiOverlayItem				me_overlay	= new PoiOverlayItem();
	private PoiOverlayItem				currentFocusedItem;
	private int							currentFocusedIndex;

	public OverlayForge(Drawable defaultMarker, Drawable selectedMarker, Drawable myLocationMarker, MapView mapView, RouteMapActivity activity) {
		super(boundCenterBottom(defaultMarker));

		me_overlay = new PoiOverlayItem();
		me_overlay.setTitle("Mi posición");
		me_overlay.setSnippet("Usted está aquí");
		me_overlay.setMarker(boundCenter(myLocationMarker));

		// Updating the markers
		this.defaultMarker = boundCenterBottom(defaultMarker);
		this.selectMarker = boundCenterBottom(selectedMarker);

		this.routeActivity = activity;
		this.context = mapView.getContext();
		this.mapView = mapView;

		this.fullList.add(me_overlay);

		this.balloonLayout = new LinearLayout(context);
		this.balloonLayout.setVisibility(View.VISIBLE);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View balloonView = inflater.inflate(R.layout.balloon_overlay, balloonLayout);

		this.balloonTitle = (TextView) balloonView.findViewById(R.id.balloon_item_title);
		this.balloonSnippet = (TextView) balloonView.findViewById(R.id.balloon_item_snippet);
		this.balloonImage = (ImageView) balloonView.findViewById(R.id.balloon_item_image);
		// This is done only to eliminate layot
		this.mapView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (balloonLayout != null)
					balloonLayout.setVisibility(LinearLayout.GONE);
				return false;
			}
		});

	}

	/**
	 * selecciona el POI indicado, poniendo el marcador rojo, centrandoe l mapa
	 * y mostrando el bocadillo
	 * 
	 * @param idx
	 *            DENTRO DEL ARRAY DE POIS, no incluyendo el marcador de
	 *            localizacion del usuario
	 */
	public void selectPOIOverlay(int idx) {
		for (int i = 0; i < m_overlays.size(); i++) {
			// Ponemos el marcador verde al resto
			Drawable d = context.getResources().getDrawable(m_overlays.get(i).getAssociatedPoi().getTipo().getDrawable());
			if(m_overlays.get(i).getAssociatedPoi().getTipo().isDrawableCenter())
				d = boundCenter(d);
			else
				d = boundCenterBottom(d);
			m_overlays.get(i).setMarker(d);
		}
		OverlayItem oi = m_overlays.get(idx);
		// Y el rojo al seleccionado
		m_overlays.get(idx).setMarker(selectMarker);
		mapView.getController().setCenter(oi.getPoint());

		doShowBallon(idx);

		mapView.invalidate();
	}

	public void addOverlay(PoiOverlayItem overlay) {

		m_overlays.add(overlay);
		fullList.add(overlay);
		populate();
	}

	public void addOverlay(PoiOverlayItem overlay, Drawable d, boolean center) {
		if (center) {
			overlay.setMarker(boundCenter(d));
		} else {
			overlay.setMarker(boundCenterBottom(d));
		}
		m_overlays.add(overlay);
		fullList.add(overlay);
		populate();
	}

	@Override
	protected PoiOverlayItem createItem(int i) {
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

		balloonTitle.setTypeface(Constants.getSubtitleFont(routeActivity.getAssets()));
		if (currentFocusedItem.getSnippet() != null) {
			balloonSnippet.setVisibility(View.VISIBLE);
			balloonSnippet.setText(currentFocusedItem.getSnippet());
		} else {
			balloonSnippet.setVisibility(View.GONE);
		}

		balloonSnippet.setTypeface(Constants.getTextFont(routeActivity.getAssets()));
		if(currentFocusedItem.getAssociatedPoi().getBalloonUrl()!=null){
			balloonImage.setVisibility(View.VISIBLE);
			Bitmap b = BitmapFactory.decodeFile(Constants.POIS_PATH + currentFocusedItem.getAssociatedPoi().getBalloonUrl());
			if (b != null) {
				balloonImage.setImageBitmap(b);
			}
			else {
				balloonImage.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.imagenenblancosmall));
			}
		} else {
			balloonImage.setVisibility(View.GONE);
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

		balloonClickRegion = balloonLayout.findViewById(R.id.balloon_main_layout);
		balloonClickRegion.setOnTouchListener(createBalloonTouchListener());

		((ViewGroup) mapView.getParent()).addView(balloonLayout, params);
	}

	@Override
	protected final boolean onTap(int index) {
		if (index == fullList.indexOf(me_overlay))
			return true;
		doShowBallon(index - 1);
		if (m_overlays.get(index - 1).isSelectable())
			routeActivity.selectPoi(index - 1);
		return true;
	}

	protected boolean onBalloonTap(int index, PoiOverlayItem item) {
		Poi whichPoi = item.getAssociatedPoi();
		// Si es un poi turistico entonces abrimos slideshow, si no lanzamos el
		// navegador ya
		if (whichPoi.getTipo() == PoiType.SimplePoi || whichPoi.getTipo() == PoiType.SecondaryPoi) {

			Intent in = new Intent(context, SlideShowActivity.class);

			in.putExtra("id_poi", whichPoi.getId());
			// in.putExtra("current", position);
			context.startActivity(in);
		} else {
			context.startActivity(Constants.launchGeoIntent(item.getTitle(), item.getPoint().getLatitude(), item.getPoint().getLongitude()));
		}
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

	protected boolean dispatchTap() {
		return false;
	}

	public void removeAllPois() {
		balloonLayout.setVisibility(LinearLayout.GONE);
		fullList.removeAll(m_overlays);
		m_overlays.clear();
		populate();
	}

	public void setMyPosition(GeoPoint location) {
		me_overlay.setPoint(location);
		requestRedraw();
	}

	public OverlayItem getMeOverlayItem() {
		return me_overlay;
	}
}