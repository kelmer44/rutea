package com.bretema.rutas.activities;

import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bretema.rutas.R;

public class SlideShowActivity extends Activity {
	private static final String LOG_TAG = SlideShowActivity.class.getSimpleName();

	private Bitmap currentBitmap;
	private Bitmap mDefaultBitmap;
	private ImageView imageView;
	private ImageButton buttonPrev;
	private ImageButton buttonNext;
	private int currentImageIndex;

	private String imguri[] = { "ruta1/imagen1.jpg", "ruta1/imagen2.jpg", "ruta1/imagen3.jpg", "ruta1/imagen4.jpg", "ruta1/imagen5.jpg", "ruta1/imagen6.jpg" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.slideshow);

		mDefaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.noimage);
		imageView = (ImageView) findViewById(R.id.imageViewCurrentImage);
		buttonPrev = (ImageButton) findViewById(R.id.imgbuttonprev);
		buttonNext = (ImageButton) findViewById(R.id.imgbuttonnext);
		
		currentImageIndex = 0;
		setCurrentImage(currentImageIndex);
		buttonPrev.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				currentImageIndex--;
				if(currentImageIndex < 0)
					currentImageIndex = imguri.length-1;
				
				setCurrentImage(currentImageIndex);
			}
		});
		
		buttonNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				currentImageIndex++;
				if(currentImageIndex == imguri.length)
					currentImageIndex = 0;
				setCurrentImage(currentImageIndex);
			}
		});

	}

	private void setCurrentImage(int idx) {
		imageView.setImageBitmap(null);
		if(currentBitmap!=null)
			currentBitmap.recycle();
		currentBitmap = null;
		try {
			currentBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeStream(getAssets().open(imguri[idx])),800, 480, true);
			imageView.setImageBitmap(currentBitmap);
		} catch (IOException e) {
			Log.d(LOG_TAG, "No se pudo abrir el recurso" + imguri[0]);
			imageView.setImageBitmap(mDefaultBitmap);
		}
	}

}
