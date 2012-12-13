package com.bretema.rutas.activities;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bretema.rutas.R;
import com.bretema.rutas.model.ruta.Ruta;
import com.bretema.rutas.service.RutaService;
import com.bretema.rutas.service.impl.RutaServiceImpl;

public class RouteDetailActivity extends Activity {

	private static final String	LOG_TAG	= RouteDetailActivity.class
												.getSimpleName();

	// Servicio desde el que abstraemos la base de datos
	private RutaService			rutaService;

	private String				id_ruta;

	// ruta con la que trabajamos
	private Ruta				ruta;

	// UI elements
	private Button				gotoRouteButton;
	private ImageView			mainRouteImage;
	private TextView			routeDetailNameLabel;
	private TextView			routeDetailDescriptionLabel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_detail);


		// obtenemos la llamada a esta activity
		Intent i = getIntent();
		// y recogemos el id de la linea que nos han pasado
		id_ruta = i.getStringExtra("id_ruta");
		
		
		rutaService = new RutaServiceImpl(getApplicationContext());
		ruta = rutaService.getRuta(Integer.parseInt(id_ruta));

		gotoRouteButton = (Button) findViewById(R.id.gotoRouteButton);
		routeDetailDescriptionLabel = (TextView) findViewById(R.id.routeDetailDescriptionLabel);
		routeDetailNameLabel = (TextView) findViewById(R.id.routeDetailNameLabel);
		mainRouteImage = (ImageView) findViewById(R.id.mainRouteImage);
		
		routeDetailDescriptionLabel.setText(ruta.getDescription());
		routeDetailNameLabel.setText(ruta.getNombre());
		try {
			mainRouteImage.setImageBitmap(BitmapFactory.decodeStream(getAssets().open(ruta.getMainImagePath())));
		} catch (IOException e) {
			Log.d(LOG_TAG, "No se pudo recuperar la imagen de ruta");
		}
		
		
		gotoRouteButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Starting new intent
				Intent in = new Intent(getApplicationContext(), RouteMapActivity.class);
				// sending pid to next activity
				in.putExtra("id_ruta", id_ruta);
				// starting new activity and expecting some response back
				startActivity(in);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_route_detail, menu);
		return true;
	}

}
