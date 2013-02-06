package com.bretema.rutas.view.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bretema.rutas.R;
import com.bretema.rutas.core.util.Constants;

public class ImageFragment extends Fragment {
	private static final String	LOG_TAG	= ImageFragment.class.getSimpleName();
	private TextView	textView;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(LOG_TAG, "initing Fragment");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.image_details, container, false);

		String imageURI = getArguments().getString("uri");
		String imageCaption = getArguments().getString("caption");
		textView = (TextView) view.findViewById(R.id.imageCaptionSlideshow);
		ImageView imageView = (ImageView) view.findViewById(R.id.imageViewSlideshow);		
		view.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					
					if(textView.getVisibility() == View.GONE){
						textView.setVisibility(View.VISIBLE);
					}
					else
						textView.setVisibility(View.GONE);
				}
				 return false;
			}
		});
		Bitmap b = BitmapFactory.decodeFile(Constants.appPath + imageURI);
		if (b != null) {
			float ratio = (float) b.getHeight() / (float) b.getWidth();
			int width = (int) Math.ceil(480 / ratio);

			imageView.setImageBitmap(Bitmap.createScaledBitmap(b, width, 480, true));
			b.recycle();
		} else {
			Log.e(LOG_TAG, "Img not found " + imageURI);
			imageView.setImageResource(R.drawable.noimage);
		}
		textView.setText(imageCaption);
		return view;
	}
}
