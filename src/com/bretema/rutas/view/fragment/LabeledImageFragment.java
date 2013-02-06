package com.bretema.rutas.view.fragment;

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

import com.bretema.rutas.R;
import com.bretema.rutas.core.util.Constants;
import com.bretema.rutas.model.media.Multimedia;
import com.bretema.rutas.service.MMService;
import com.bretema.rutas.service.impl.MMServiceImpl;

public class LabeledImageFragment extends MultimediaFragment {
	private static final String	LOG_TAG	= LabeledImageFragment.class.getSimpleName();

	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		

		View view = inflater.inflate(R.layout.image_details, container, false);
		TextView textView = (TextView) view.findViewById(R.id.imageCaptionSlideshow);
		ImageView imageView = (ImageView) view.findViewById(R.id.imageViewSlideshow);
		Bitmap b = BitmapFactory.decodeFile(Constants.appPath + getMultimedia().getUri());
		if (b != null) {
			float ratio = (float) b.getHeight() / (float) b.getWidth();
			int width = (int) Math.ceil(480 / ratio);

			imageView.setImageBitmap(Bitmap.createScaledBitmap(b, width, 480, true));
			b.recycle();
		} else {
			Log.e(LOG_TAG, "Img not found " + getMultimedia().getUri());
			imageView.setImageResource(R.drawable.noimage);
		}
		//textView.setText(imageCaption);
		return view;
	}
}
