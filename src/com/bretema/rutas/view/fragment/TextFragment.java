package com.bretema.rutas.view.fragment;

import com.bretema.rutas.R;
import com.bretema.rutas.core.util.Constants;
import com.bretema.rutas.service.impl.MMServiceImpl;

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

public class TextFragment  extends MultimediaFragment{
	private static final String	LOG_TAG	= TextFragment.class.getSimpleName();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		

		View view = inflater.inflate(R.layout.fragment_text, container, false);
		TextView textView = (TextView) view.findViewById(R.id.multimediaTextView);
		String imageCaption = getMultimedia().getDescripcion();
		imageCaption = imageCaption.replace("\\n", "\n");
		imageCaption = imageCaption.replace("\\r", "\r");
		textView.setTypeface(Constants.getTextFont(getActivity().getAssets()));
		textView.setText(imageCaption);
		textView.setVisibility(View.VISIBLE);
		return view;
	}

	@Override
	public void onPageIsChanged() {
		// TODO Auto-generated method stub
		
	}
}
