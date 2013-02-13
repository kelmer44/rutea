package com.bretema.rutas.view.fragment;

import java.io.FileDescriptor;
import java.io.PrintWriter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bretema.rutas.R;
import com.bretema.rutas.core.util.Constants;
import com.bretema.rutas.view.ConstantAnchorMediaController;

public class VideoFragment extends MultimediaFragment {
	private static final String				LOG_TAG	= VideoFragment.class.getSimpleName();

	private VideoView						mVideoView;
	private ConstantAnchorMediaController	mMedia;
	// NO reproducimos si el video no se ha hallado o no funciona
	private boolean							doPlay	= true;

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
		mVideoView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if(mMedia.isShown())
						mMedia.hide();
					else
						mMedia.show();
				}
				return true;
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
		mVideoView = (VideoView)view.findViewById(R.id.videoViewSlideShow);
        super.onViewCreated(view, savedInstanceState);
    }
	
	@Override
	public void onPageIsChanged() {
		if(mVideoView!=null){
			if(mVideoView.isPlaying()){
				mVideoView.pause();
				mVideoView.seekTo(0);
			}
			mMedia.hide();
		}
	}

	
	
}
