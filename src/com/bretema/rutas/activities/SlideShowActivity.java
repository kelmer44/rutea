package com.bretema.rutas.activities;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bretema.rutas.R;
import com.bretema.rutas.view.ImageFragment;

public class SlideShowActivity extends FragmentActivity {
	private static final String LOG_TAG = SlideShowActivity.class.getSimpleName();

	private MyAdapter mAdapter;
	private ViewPager mPager;
	private String imguri[] = { "ruta1/imagen1.jpg", "ruta1/imagen2.jpg", "ruta1/imagen3.jpg", "ruta1/imagen4.jpg", "ruta1/imagen5.jpg", "ruta1/imagen6.jpg", "ruta1/imagen7.jpg", "ruta1/imagen8.jpg", "ruta1/imagen9.jpg", "ruta1/imagen10.jpg" };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.slideshow);
		mAdapter = new MyAdapter(getSupportFragmentManager());

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
	}

	public class MyAdapter extends FragmentPagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return imguri.length;
		}

		@Override
		public Fragment getItem(int position) {
			
				try {
					return new ImageFragment(Bitmap.createScaledBitmap(BitmapFactory.decodeStream(getAssets().open(imguri[position])),800, 480, true));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
		}
	}

}
