package com.bretema.rutas.activities;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bretema.rutas.R;
import com.bretema.rutas.model.ruta.Ruta;
import com.bretema.rutas.service.RutaService;
import com.bretema.rutas.service.impl.RutaServiceImpl;
import com.bretema.rutas.view.ImageGridAdapter;
import com.bretema.rutas.view.RouteListAdapter;

/**
 * Representa la lista de rutas.
 * 
 * TODO: Transformar en una lista de imágenes (Table View)
 * 
 * @author kelmer
 * 
 */
public class RouteListActivity extends ListActivity {

	private static final String LOG_TAG = RouteListActivity.class.getSimpleName();

	// Servicio desde el que abstraemos la base de datos
	private RutaService rutaService;

	// Lista de rutas
	private List<Ruta> routeList;

	// list view de la ListActivity
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		//requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_route_list);

		//obtenemos el servicio
		rutaService = new RutaServiceImpl(getApplicationContext());
		Log.d(LOG_TAG, "Obteniendo lista de rutas...");
		routeList = rutaService.findAll();

		ListAdapter adapter = new RouteListAdapter(RouteListActivity.this, routeList);
		// updating listview
		setListAdapter(adapter);

		ListView lv = getListView();
		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new ImageGridAdapter(getApplicationContext(), routeList));
		
		// Al pulsar un elemento de la lista se pasa a la vista detallada de
		// línea
		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				// getting values from selected ListItem
				String id_ruta = ((TextView) view.findViewById(R.id.id_ruta)).getText().toString();

				Log.d(LOG_TAG, "Trying to get route for id " + id_ruta + "...");
				
				Intent in = new Intent(getApplicationContext(), RouteDetailActivity.class);
				in.putExtra("id_ruta", id_ruta);
				startActivity(in);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_route_list, menu);
		return true;
	}

}
