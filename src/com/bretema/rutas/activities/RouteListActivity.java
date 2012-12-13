package com.bretema.rutas.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bretema.rutas.R;
import com.bretema.rutas.model.ruta.Ruta;
import com.bretema.rutas.service.RutaService;
import com.bretema.rutas.service.impl.RutaServiceImpl;
import com.bretema.rutas.view.RouteListAdapter;

public class RouteListActivity extends ListActivity {

	private static final String LOG_TAG = RouteListActivity.class.getSimpleName();
	
	//Servicio desde el que abstraemos la base de datos 
	private RutaService rutaService;
	
	//Lista de rutas
	private List<Ruta> routeList;
	
	//list view de la ListActivity
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_list);

		rutaService = new RutaServiceImpl(getApplicationContext());
		//rutaService.deleteAllRutas();
		//rutaService.save(new Ruta("Ribeira sacra", "Un paseiño polos cañons do sil", "rsa.jpg"));
		//rutaService.save(new Ruta("Monforte salvaxe", "Onde cazar xabaríns", "rsa.jpg"));
		
		routeList = new ArrayList<Ruta>();		
		routeList = rutaService.findAll();
		
		ListAdapter adapter = new RouteListAdapter(RouteListActivity.this, routeList);
		// updating listview
		setListAdapter(adapter);
		
		ListView lv = getListView();
		
		// Al pulsar un elemento de la lista se pasa a la vista detallada de línea
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


					// getting values from selected ListItem
					String id_ruta = ((TextView) view.findViewById(R.id.id_ruta)).getText().toString();

					Log.d(LOG_TAG, "Tryin to get route " + id_ruta);
					// Starting new intent
					Intent in = new Intent(getApplicationContext(),	RouteDetailActivity.class);
					// sending pid to next activity
					in.putExtra("id_ruta", id_ruta);
					// starting new activity and expecting some response back
					startActivity(in);
				}
			}
		);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_route_list, menu);
		return true;
	}

}
