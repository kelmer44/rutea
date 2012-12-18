package com.bretema.rutas.map;

import java.util.ArrayList;
import java.util.List;

import org.mapsforge.android.maps.MapController;
import org.mapsforge.android.maps.MapView;
import org.mapsforge.android.maps.overlay.ItemizedOverlay;
import org.mapsforge.android.maps.overlay.Overlay;
import org.mapsforge.android.maps.overlay.OverlayItem;

import android.content.Context;
import android.content.Intent;
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

public class OverlayForge extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem>		m_overlays	= new ArrayList<OverlayItem>();
	private Context						c;
	private OverlayItem					currentFocusedItem;
	private View						clickRegion;
	private int							currentFocusedIndex;
	private int							viewOffset;
	private MapView						mapView;
	private BalloonOverlay<OverlayItem>	balloonView;
	private LinearLayout				layout;
	private TextView					title;
	private TextView					snippet;
	private Drawable					defaultMarker;
	private Drawable					selectMarker;
	private RouteMapActivity			activity;

	public OverlayForge(Drawable defaultMarker, Drawable selectMarker, MapView mapView, RouteMapActivity activity) {
		super(boundCenterBottom(defaultMarker));
		this.defaultMarker = boundCenterBottom(defaultMarker);
		this.selectMarker = boundCenterBottom(selectMarker);
		this.activity = activity;
		// super(defaultMarker);
		this.c = mapView.getContext();
		this.mapView = mapView;
		this.mapView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (layout != null)
					layout.setVisibility(LinearLayout.GONE);
				return false;
			}
		});
	}

	public void selectOverlay(int idx) {
		for (int i = 0; i < this.size(); i++) {
			getOverlay(i).setMarker(defaultMarker);
		}
		getOverlay(idx).setMarker(selectMarker);
	}

	public void addOverlay(OverlayItem overlay) {
		m_overlays.add(overlay);
		populate();
	}

	public OverlayItem getOverlay(int i) {
		return m_overlays.get(i);
	}

	@Override
	protected OverlayItem createItem(int i) {
		return m_overlays.get(i);
	}

	@Override
	public int size() {
		return m_overlays.size();
	}

	public void doShowBallon(int index) {
		currentFocusedIndex = index;
		currentFocusedItem = createItem(index);

		List<Overlay> mapOverlays = mapView.getOverlays();
		if (mapOverlays.size() >= 1) {
			hideOtherBalloons(mapOverlays);
		}

		((ViewGroup) mapView.getParent()).removeView(layout);

		MapController mMapController = mapView.getController();
		mMapController.setCenter(currentFocusedItem.getPoint());

		layout = new LinearLayout(c);
		layout.setVisibility(View.VISIBLE);

		LayoutInflater inflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.balloon_overlay, layout);

		title = (TextView) v.findViewById(R.id.balloon_item_title);
		snippet = (TextView) v.findViewById(R.id.balloon_item_snippet);

		layout.setVisibility(View.VISIBLE);
		if (currentFocusedItem.getTitle() != null) {
			title.setVisibility(View.VISIBLE);
			title.setText(currentFocusedItem.getTitle());
		} else {
			title.setVisibility(View.GONE);
		}

		if (currentFocusedItem.getSnippet() != null) {
			snippet.setVisibility(View.VISIBLE);
			snippet.setText(currentFocusedItem.getSnippet());
		} else {
			snippet.setVisibility(View.GONE);
		}

		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;

		layout.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		params.topMargin -= layout.getMeasuredHeight()
				- (layout.getMeasuredHeight() / 2) + viewOffset;

		ImageView imgClose = (ImageView) layout
				.findViewById(R.id.close_img_button);
		imgClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				layout.setVisibility(View.GONE);
				((ViewGroup) mapView.getParent()).removeView(layout);
			}

		});

		clickRegion = layout.findViewById(R.id.balloon_inner_layout);
		clickRegion.setOnTouchListener(createBalloonTouchListener());

		((ViewGroup) mapView.getParent()).addView(layout, params);
	}

	@Override
	protected final boolean onTap(int index) {

		doShowBallon(index);
		activity.selectPoi(index);
		return true;
	}

	protected boolean onBalloonTap(int index, OverlayItem item) {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q="
				+ item.getPoint().getLatitude()
				+ ","
				+ item.getPoint().getLongitude()));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		c.startActivity(intent);
		/*
		 * Intent i = null; i = new Intent(c,LSNetInfoActivity.class);
		 * 
		 * if (i!=null){ Bundle bundle = new Bundle();
		 * 
		 * bundle.putParcelable("NETWORK_OBJ", m_networks.get(index));
		 * 
		 * i.putExtras(bundle);
		 * 
		 * c.startActivity(i); }
		 */
		return true;
	}

	protected void hideBalloon() {
		if (balloonView != null) {
			balloonView.setVisibility(View.GONE);
		}
	}

	public void hideAllBallooons() {
		for (Overlay overlay : mapView.getOverlays()) {
			if (overlay instanceof OverlayForge && overlay != this) {
				((OverlayForge) overlay).hideBalloon();

			}
		}
	}

	private void hideOtherBalloons(List<Overlay> overlays) {

		for (Overlay overlay : overlays) {
			if (overlay instanceof OverlayForge && overlay != this) {
				((OverlayForge) overlay).hideBalloon();

			}
		}

	}

	private OnTouchListener createBalloonTouchListener() {
		return new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {

				View l = ((View) v.getParent())
						.findViewById(R.id.balloon_main_layout);
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

	protected BalloonOverlay<OverlayItem> createBalloonOverlayView() {
		return new BalloonOverlay<OverlayItem>(getMapView().getContext(), getBalloonBottomOffset());
	}

	protected MapView getMapView() {
		return mapView;
	}

	public void setBalloonBottomOffset(int pixels) {
		viewOffset = pixels;
	}

	public int getBalloonBottomOffset() {
		return viewOffset;
	}
}