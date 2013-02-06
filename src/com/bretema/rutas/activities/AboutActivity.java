package com.bretema.rutas.activities;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bretema.rutas.R;
import com.bretema.rutas.core.util.Constants;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_about);
		
		Typeface colab = Typeface.createFromAsset(getAssets(), "ColabMed.ttf");
		TextView textViewAppName = (TextView)findViewById(R.id.textViewAppName);
		TextView textViewAppDesc = (TextView)findViewById(R.id.textViewAppDesc);
		textViewAppName.setTypeface(Constants.getSubtitleFont(getAssets()));
		textViewAppDesc.setTypeface(Constants.getSubtitleFont(getAssets()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_about, menu);
		return true;
	}

}
