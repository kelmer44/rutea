package com.bretema.rutas.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.bretema.rutas.R;

/**
 * Creating splash screen for the app
 * 
 * @author Gabriel
 * 
 */
public class SplashActivity extends Activity {

	private static final int	DELAY_TIME	= 200; // millis to wait before
													// starting home act.
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
