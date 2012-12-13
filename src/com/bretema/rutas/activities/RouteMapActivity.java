package com.bretema.rutas.activities;

import java.io.File;
import java.io.IOException;

import org.mapsforge.android.maps.MapActivity;
import org.mapsforge.android.maps.MapController;
import org.mapsforge.android.maps.MapView;
import org.mapsforge.map.reader.header.FileOpenResult;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
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

import com.bretema.rutas.R;

public class RouteMapActivity extends MapActivity {

	private static final String	LOG_TAG		= RouteMapActivity.class
													.getSimpleName();

	private MapView				mapView;
	private MapController		mapController;
	private Gallery				selectedPOIgallery;
	private Context				mContext;
	private AssetManager		assetManager;
	
	//Static images for the moment
	private String[]			mThumbIds	= {"ruta1/thumb1.jpg",
			"ruta1/thumb2.jpg", "ruta1/thumb3.jpg", "ruta1/thumb4.jpg",
			"ruta1/thumb5.jpg", "ruta1/thumb6.jpg", "ruta1/thumb7.jpg",
			"ruta1/thumb8.jpg", "ruta1/thumb9.jpg", "ruta1/thumb10.jpg" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_map);

		
		assetManager = getAssets();
		
		
		selectedPOIgallery = (Gallery) findViewById(R.id.selectedPOIgallery);
		selectedPOIgallery.setAdapter(new ImageAdapter(this));

		mapView = (MapView) findViewById(R.id.mapView);
		mapView.setClickable(true);
		mapView.setBuiltInZoomControls(true);

		mapController = mapView.getController();

		File mapFile = new File(Environment.getExternalStorageDirectory().getPath()
				+ "/maps/galicia.map");

		Log.d(LOG_TAG, "Trying to load file" + mapFile.getName());
		
		FileOpenResult fileOpenResult = mapView.setMapFile(mapFile);
		if (!fileOpenResult.isSuccess()) {
			Log.d(LOG_TAG, "Map file could not be loaded");
			mapView.setEnabled(false);
			AlertDialog.Builder builder = new AlertDialog.Builder(RouteMapActivity.this);
			builder.setMessage(getResources().getString(R.string.no_map));
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					RouteMapActivity.this.finish();
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
			mapView.setWillNotDraw(false);
			mapView = null;
			return;
		}
		else {
			Log.d(LOG_TAG, "Map file loaded successfully");
		}
	}

	public class ImageAdapter extends BaseAdapter {
		public ImageAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return mThumbIds.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView i = new ImageView(mContext);

			try {
				i.setImageBitmap(BitmapFactory.decodeStream(assetManager.open(mThumbIds[position])));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.d(LOG_TAG, "No se pudo abrir el recurso" + mThumbIds[position]);
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
