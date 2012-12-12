package com.bretema.rutas.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListAdapter;

import com.bretema.rutas.R;
import com.bretema.rutas.model.ruta.Ruta;
import com.bretema.rutas.service.RutaService;
import com.bretema.rutas.service.impl.RutaServiceImpl;
import com.bretema.rutas.view.RouteListAdapter;

public class RouteListActivity extends ListActivity {

	private static final String LOG_TAG = RouteListActivity.class.getSimpleName();
	
	private List<Ruta> routeList;
	
	private RutaService rutaService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_list);

		rutaService = new RutaServiceImpl(getApplicationContext());
		
		routeList = new ArrayList<Ruta>();		
		
		routeList = rutaService.findAll();
		
		ListAdapter adapter = new RouteListAdapter(RouteListActivity.this, routeList);
		// updating listview
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_route_list, menu);
		return true;
	}

}
