package com.bretema.rutas.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;

import com.bretema.rutas.R;

public class RouteListActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_route_list, menu);
		return true;
	}

}
