package com.bretema.rutas.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bretema.rutas.R;

public class ImageFragment extends Fragment {

	 private final Bitmap imageURI;
	    
	    public ImageFragment(Bitmap uri){
	    	imageURI = uri;
	    }
	 
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        Log.e("Test", "hello");
	    }
	 
	    @Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	        super.onActivityCreated(savedInstanceState);
	 
	    }
	 
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        View view = inflater.inflate(R.layout.image_details, container, false);
	        
	         ImageView imageView = (ImageView) view.findViewById(R.id.imageView1);
		       
	        imageView.setImageBitmap(imageURI);
	        return view;
	    }
}
