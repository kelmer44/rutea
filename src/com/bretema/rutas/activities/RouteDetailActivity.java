package com.bretema.rutas.activities;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bretema.rutas.R;
import com.bretema.rutas.model.ruta.Ruta;
import com.bretema.rutas.service.RutaService;
import com.bretema.rutas.service.impl.RutaServiceImpl;

/**
 * Detalles de la ruta, pantalla de bienvenida antes de mostrar la ruta en
 * pantalla.
 * 
 * @author kelmer
 * 
 */
public class RouteDetailActivity extends Activity {

	private static final String	LOG_TAG	= RouteDetailActivity.class.getSimpleName();

	// Servicio desde el que abstraemos la base de datos
	private RutaService			rutaService;

	// Id de la ruta actual
	private String				id_ruta;

	// ruta con la que trabajamos
	private Ruta				ruta;

	// UI elements
	private Button				gotoRouteButton;
	private ImageView			mainRouteImage;
	private TextView			routeDetailNameLabel;
	private TextView			routeDetailDescriptionLabel;

	private MediaPlayer			mediaPlayer;
	private ImageButton			playButton;

	private AssetFileDescriptor	afd;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_detail);

		// obtenemos la llamada a esta activity
		Intent i = getIntent();
		// y recogemos el id de la rutaa que nos han pasado
		id_ruta = i.getStringExtra("id_ruta");

		// Acceso a db
		Log.d(LOG_TAG, "Obteniendo datos de ruta con id" + id_ruta + "...");
		rutaService = new RutaServiceImpl(getApplicationContext());
		ruta = rutaService.getRuta(Integer.parseInt(id_ruta));

		// Obtencion de los elementos del UI
		gotoRouteButton = (Button) findViewById(R.id.gotoRouteButton);
		routeDetailDescriptionLabel = (TextView) findViewById(R.id.routeDetailDescriptionLabel);
		routeDetailDescriptionLabel.setMovementMethod(new ScrollingMovementMethod());
		routeDetailNameLabel = (TextView) findViewById(R.id.routeDetailNameLabel);
		mainRouteImage = (ImageView) findViewById(R.id.mainRouteImage);
		
		
		
		// Asignación de valores
		routeDetailDescriptionLabel.setText(ruta.getDescription());
		Typeface yanone = Typeface.createFromAsset(getAssets(), "YanoneKaffeesatz-Light.ttf");
		Typeface colab = Typeface.createFromAsset(getAssets(), "ColabReg.ttf");
		Typeface colabMed = Typeface.createFromAsset(getAssets(), "ColabMed.ttf");
		routeDetailNameLabel.setText(ruta.getNombre());
		routeDetailNameLabel.setTypeface(yanone);
		//routeDetailNameLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
//		Display display = getWindowManager().getDefaultDisplay();
//		Point size = new Point();
//		display.getSize(size);
//		int width = size.x;
//		int height = size.y;
		
		
		routeDetailDescriptionLabel.setTypeface(colab);
		gotoRouteButton.setTypeface(colabMed);

		loadAudioFile();

		gotoRouteButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(getApplicationContext(), RouteMapActivity.class);
				in.putExtra("id_ruta", id_ruta);
				startActivity(in);
			}
		});
	}

	private void loadAudioFile() {
		
		Log.d(LOG_TAG, "Trying to load audio file...");
		mediaPlayer = new MediaPlayer();
		try {
			afd = getAssets().openFd("ruta1/intro.mp3");
			mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
			mediaPlayer.prepare();
			mediaPlayer.start();
		} catch (IOException e1) {
			Log.e(LOG_TAG, "Could not open file " + "ruta1/intro.mp3" + " for playback.", e1);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_route_detail, menu);
		return true;
	}
	
	@Override
	protected void onStop() {
	    super.onStop();
	    try {
			afd.close();
		} catch (IOException e) {
			Log.e(LOG_TAG, "Could not close audio file!");
			e.printStackTrace();
		}
	    // deallocate all memory
	    if (mediaPlayer != null) {
	        if (mediaPlayer.isPlaying()) {
	        	mediaPlayer.stop();
	        }
	        mediaPlayer.release();
	        mediaPlayer = null;
	    }
	}

}
