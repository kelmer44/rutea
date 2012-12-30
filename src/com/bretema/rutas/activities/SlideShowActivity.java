package com.bretema.rutas.activities;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;

import com.bretema.rutas.R;
import com.bretema.rutas.enums.MMType;
import com.bretema.rutas.model.media.Multimedia;
import com.bretema.rutas.model.poi.Poi;
import com.bretema.rutas.service.PoiService;
import com.bretema.rutas.service.impl.PoiServiceImpl;
import com.bretema.rutas.view.ImageFragment;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class SlideShowActivity extends FragmentActivity {
	private static final String		LOG_TAG	= SlideShowActivity.class.getSimpleName();

	private MyAdapter				mAdapter;
	private ViewPager				mPager;

	private Integer					poiId;

	private Poi						currentPoi;
	private PoiService				poiService;
	private PageIndicator			mIndicator;
	private ArrayList<Multimedia>	multimediaList;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.slideshow);

		// obtenemos la llamada a esta activity
		Intent i = getIntent();
		// y recogemos el id del poi que nos han pasado
		poiId = i.getIntExtra("id_poi", 0);
		Integer currentItem = i.getIntExtra("current", 0);
		poiService = new PoiServiceImpl(getApplicationContext());

		currentPoi = poiService.getPoi(poiId);

		multimediaList = new ArrayList<Multimedia>();

		multimediaList.addAll(currentPoi.getMedia());

		mAdapter = new MyAdapter(getSupportFragmentManager());

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setCurrentItem(currentItem);
		mPager.setAdapter(mAdapter);

		CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
		mIndicator = indicator;
		indicator.setViewPager(mPager);
		indicator.setSnap(true);
	}

	public class MyAdapter extends FragmentPagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return multimediaList.size();
		}

		@Override
		public Fragment getItem(int position) {
			Multimedia m = multimediaList.get(position);
			if (m.getTipo() == MMType.Imagen) {
				ImageFragment ifragment = new ImageFragment();
				Bundle bundle = new Bundle();
				bundle.putString("uri", m.getUri());
				bundle.putString("caption", m.getNombre());
				ifragment.setArguments(bundle);
				return ifragment;
			}
			return null;
		}
	}

}
