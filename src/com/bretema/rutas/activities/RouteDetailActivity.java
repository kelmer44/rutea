package com.bretema.rutas.activities;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.TypedValue;
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

/**
 * Detalles de la ruta, pantalla de bienvenida antes de mostrar
 * la ruta en pantalla.
 * @author kelmer
 * 
 */
public class RouteDetailActivity extends Activity {

	private static final String LOG_TAG = RouteDetailActivity.class.getSimpleName();

	// Servicio desde el que abstraemos la base de datos
	private RutaService rutaService;

	//Id de la ruta actual
	private String id_ruta;

	// ruta con la que trabajamos
	private Ruta ruta;

	// UI elements
	private Button gotoRouteButton;
	private ImageView mainRouteImage;
	private TextView routeDetailNameLabel;
	private TextView routeDetailDescriptionLabel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_detail);

		// obtenemos la llamada a esta activity
		Intent i = getIntent();
		// y recogemos el id de la rutaa que nos han pasado
		id_ruta = i.getStringExtra("id_ruta");

		
		//Acceso a db
		Log.d(LOG_TAG, "Obteniendo datos de ruta con id" + id_ruta + "...");
		rutaService = new RutaServiceImpl(getApplicationContext());
		ruta = rutaService.getRuta(Integer.parseInt(id_ruta));

		
		
		//Obtencion de los elementos del UI
		gotoRouteButton = (Button) findViewById(R.id.gotoRouteButton);
		routeDetailDescriptionLabel = (TextView) findViewById(R.id.routeDetailDescriptionLabel);
		routeDetailDescriptionLabel.setMovementMethod(new ScrollingMovementMethod());
		routeDetailNameLabel = (TextView) findViewById(R.id.routeDetailNameLabel);
		mainRouteImage = (ImageView) findViewById(R.id.mainRouteImage);

		//Asignación de valores
		routeDetailDescriptionLabel.setText(ruta.getDescription());
		Typeface yanone = Typeface.createFromAsset(getAssets(), "YanoneKaffeesatz-Light.ttf");
		Typeface colab = Typeface.createFromAsset(getAssets(), "ColabReg.ttf");
		Typeface colabMed = Typeface.createFromAsset(getAssets(), "ColabMed.ttf");
		routeDetailNameLabel.setText(ruta.getNombre());
		routeDetailNameLabel.setTypeface(yanone);
		routeDetailNameLabel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 48);
		routeDetailDescriptionLabel.setTypeface(colab);
		gotoRouteButton.setTypeface(colabMed);
		
		
		
		
		try {
			mainRouteImage.setImageBitmap(BitmapFactory.decodeStream(getAssets().open(ruta.getMainImagePath())));
		} catch (IOException e) {
			Log.e(LOG_TAG, "No se pudo recuperar la imagen de ruta");
		}

		gotoRouteButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(getApplicationContext(), RouteMapActivity.class);
				in.putExtra("id_ruta", id_ruta);
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
