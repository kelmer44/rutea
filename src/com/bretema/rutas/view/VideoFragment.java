package com.bretema.rutas.view;

import java.io.IOException;

import com.bretema.rutas.R;

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
import android.widget.VideoView;

public class VideoFragment extends Fragment{
	private static final String LOG_TAG = VideoFragment.class.getSimpleName();
	
	
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
		View view = inflater.inflate(R.layout.video_details, container, false);
		String imageURI = getArguments().getString("uri");
		String videoCaption = getArguments().getString("caption");
		TextView textView = (TextView) view.findViewById(R.id.imageCaptionSlideshow);
		VideoView videoView = (VideoView) view.findViewById(R.id.videoViewSlideShow);
		videoView.setVideoPath("android.resource://com.bretema.rutas/raw/video");
		videoView.start();
		textView.setText(videoCaption);
		return view;
	}
}
