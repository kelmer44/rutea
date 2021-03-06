package com.bretema.rutas.view.fragment;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bretema.rutas.R;
import com.bretema.rutas.core.util.Constants;
import com.bretema.rutas.view.ConstantAnchorMediaController;

public class poiInfoFragment extends MultimediaFragment {
	private static final String				LOG_TAG	= VideoFragment.class.getSimpleName();

	private VideoView						mVideoView;
	private ConstantAnchorMediaController	mMedia;
	
	private TextView                        textView;
	// NO reproducimos si el video no se ha hallado o no funciona
	//private boolean							doPlay	= true;

	//private ImageView playButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		
		View view = inflater.inflate(R.layout.fragment_text, container, false);
		textView = (TextView) view.findViewById(R.id.poiDetailDescriptionLabel);
		String imageCaption = getMultimedia().getDescByLocale(getLocaleFromMainActivity());
		imageCaption = imageCaption.replace("\\n", "\n");
		imageCaption = imageCaption.replace("\\r", "\r");
		textView.setTypeface(Constants.getTextFont(getActivity().getAssets()));
		textView.setText(imageCaption);

		textView.setMovementMethod(new ScrollingMovementMethod());
		textView.setVisibility(View.VISIBLE);
		
		String videoCaption = getMultimedia().getDescripcion();
		
		view = inflater.inflate(R.layout.video_details, container, false);
				
		mVideoView = (VideoView) view.findViewById(R.id.mainPoiVideo);

		mMedia = new ConstantAnchorMediaController(getActivity(), mVideoView);

		mMedia.setMediaPlayer(mVideoView);
		mMedia.setPadding(0, 0, 0, 50);
		mVideoView.setMediaController(mMedia);
		mVideoView.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				mVideoView.setBackgroundColor(0);
				mVideoView.start();

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
		mVideoView.setVideoPath(Constants.APP_PATH + getMultimedia().getUri());
		mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {

			public boolean onError(MediaPlayer mp, int what, int extra) {
				Toast.makeText(getActivity(), "El v�deo no se ha encontrado o no es reproducible", Toast.LENGTH_LONG).show();
				mVideoView.setBackgroundColor(0);
				return true;
			}

		});
		
		//icono de play sobre el v�deo
		/*playButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!mVideoView.isPlaying()){
					mVideoView.start();
				}
				playButton.setVisibility(View.INVISIBLE);
			}
		});
		*/
		
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
			//mMedia.hide();
			//playButton.setVisibility(View.VISIBLE);
		}
	}
	
	
}

