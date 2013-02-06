package com.bretema.rutas.view.fragment;

import com.bretema.rutas.R;
import com.bretema.rutas.core.util.Constants;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TextFragment  extends Fragment{
	private static final String	LOG_TAG	= TextFragment.class.getSimpleName();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		

		View view = inflater.inflate(R.layout.image_details, container, false);
		TextView textView = (TextView) view.findViewById(R.id.imageCaptionSlideshow);
		String imageCaption = getArguments().getString("caption");

		textView.setText(imageCaption);
		textView.setVisibility(View.VISIBLE);
		return view;
	}
}
