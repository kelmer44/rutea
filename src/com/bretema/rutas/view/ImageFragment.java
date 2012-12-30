package com.bretema.rutas.view;

import java.io.IOException;

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

public class ImageFragment extends Fragment {
	private static final String LOG_TAG = ImageFragment.class.getSimpleName();

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
		TextView textView = (TextView) view.findViewById(R.id.imageCaption);
		ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
		try {
			Bitmap b = BitmapFactory.decodeStream(getActivity().getApplicationContext().getAssets().open(imageURI));
			float ratio = (float) b.getHeight() / (float) b.getWidth();
			int width = (int) Math.ceil(480 / ratio);

			imageView.setImageBitmap(Bitmap.createScaledBitmap(b, width, 480, true));
			b.recycle();
		} catch (IOException e) {

			Log.e(LOG_TAG, "Img not found " + imageURI);
			imageView.setImageResource(R.drawable.noimage);
		}
		textView.setText(imageCaption);
		return view;
	}
}
