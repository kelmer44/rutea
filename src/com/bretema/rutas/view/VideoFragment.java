package com.bretema.rutas.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bretema.rutas.R;

public class VideoFragment extends Fragment{
	private static final String LOG_TAG = VideoFragment.class.getSimpleName();
	
	private VideoView mVideoView;
	private ConstantAnchorMediaController mMedia;
	   
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
		mVideoView = (VideoView) view.findViewById(R.id.videoViewSlideShow);
		
		
		mMedia = new ConstantAnchorMediaController(getActivity(), mVideoView);
		
		mMedia.setMediaPlayer(mVideoView);
		mMedia.setPadding(0, 0, 0, 50);
	    mVideoView.setMediaController(mMedia);
		mVideoView.setVideoPath("android.resource://com.bretema.rutas/raw/video");
		textView.setText(videoCaption);
		return view;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mMedia.hide();
		mVideoView.stopPlayback();
	}
}
