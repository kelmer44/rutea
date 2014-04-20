
package com.bretema.rutas.view.fragment;

import com.bretema.rutas.R;
import com.bretema.rutas.core.util.Constants;
import com.bretema.rutas.service.impl.MMServiceImpl;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class TextFragment extends MultimediaFragment implements MediaPlayer.OnPreparedListener {
    private static final String LOG_TAG = TextFragment.class.getSimpleName();
    private MediaPlayer mediaPlayer;
    private ToggleButton playButton;
    private ImageView stopButton;
    private String audioUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_text, container, false);
        TextView textView = (TextView) view.findViewById(R.id.multimediaTextView);
        String imageCaption = getMultimedia().getDescByLocale(this.getLocaleFromMainActivity());
        imageCaption = imageCaption.replace("\\n", "\n");
        imageCaption = imageCaption.replace("\\r", "\r");
        textView.setTypeface(Constants.getTextFont(getActivity().getAssets()));
        textView.setText(imageCaption);

        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setVisibility(View.VISIBLE);
        audioUri = getMultimedia().getUri();
        playButton = (ToggleButton) view.findViewById(R.id.playButton);
        stopButton = (ImageView) view.findViewById(R.id.stopButton);

        playButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (playButton.isChecked()) {
                    mediaPlayer.start();
                } else {
                    mediaPlayer.pause();
                }
            }
        });

        stopButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
                playButton.setChecked(false);
            }
        });
        return view;
    }


    private void loadAudioFile() {

        Log.d(LOG_TAG, "Trying to load audio file...");
        try {
            mediaPlayer.setDataSource(Constants.APP_PATH + audioUri);
            mediaPlayer.prepareAsync();
        } catch (IOException e1) {
            Log.e(LOG_TAG, "Could not open file " + audioUri + " for playback.", e1);
        }

    }
    
    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);

        playButton.setChecked(false);
        loadAudioFile();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d(LOG_TAG, "onPrepared ready");
        // mediaPlayer.start();
        playButton.setEnabled(true);
    }

    @Override
    public void onStop() {
        super.onStop();

        // deallocate all memory
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    
    @Override
    public void onPageIsChanged() {
        if(mediaPlayer!=null){
            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
        }
    }

    
}
