package com.bretema.rutas.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.bretema.rutas.R;

/**
 * Creating splash screen for the app
 * 
 * @author Gabriel
 * 
 */
public class SplashActivity extends Activity {

	private static final int	DELAY_TIME	= 2000; // millis to wait before
													// starting home act.
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_layout);
		
		Handler handler = new Handler();
		
		// run a thread after 2 seconds to start the home screen
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				
				finish();
				//start the home Screen
				
				Intent intent = new Intent(SplashActivity.this, RouteListActivity.class);
				SplashActivity.this.startActivity(intent);
				
			}
		}, DELAY_TIME);
		
		
	}

}
