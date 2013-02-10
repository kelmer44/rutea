package com.bretema.rutas.view.fragment;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bretema.rutas.R;
import com.bretema.rutas.core.util.Constants;
import com.bretema.rutas.view.ConstantAnchorMediaController;

public class VideoFragment extends MultimediaFragment{
	private static final String LOG_TAG = VideoFragment.class.getSimpleName();
	
	private VideoView mVideoView;
	private ConstantAnchorMediaController mMedia;
	//NO reproducimos si el video no se ha hallado o no funciona
	private boolean doPlay = true;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.video_details, container, false);
		String imageURI = getMultimedia().getUri();
		String videoCaption = getMultimedia().getDescripcion();
		TextView textView = (TextView) view.findViewById(R.id.imageCaptionSlideshow);
		mVideoView = (VideoView) view.findViewById(R.id.videoViewSlideShow);
		
		
		mMedia = new ConstantAnchorMediaController(getActivity(), mVideoView);
		
		mMedia.setMediaPlayer(mVideoView);
		mMedia.setPadding(0, 0, 0, 50);
	    mVideoView.setMediaController(mMedia);
	    mVideoView.setOnPreparedListener(new OnPreparedListener() {
			
			@Override
			public void onPrepared(MediaPlayer mp) {
				mVideoView.setBackgroundColor(0);
				
			}
		});
		mVideoView.setVideoPath(Constants.appPath + getMultimedia().getUri());
		mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {

	        public boolean onError(MediaPlayer mp, int what, int extra) {
	        	Toast.makeText(getActivity(), "El vídeo no se ha encontrado o no es reproducible", Toast.LENGTH_LONG).show();
	        	mVideoView.setBackgroundColor(0);
	            return true;
	        }

	    });
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
