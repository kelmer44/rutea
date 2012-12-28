package com.bretema.rutas.view;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bretema.rutas.R;
import com.bretema.rutas.activities.SlideShowActivity;
import com.bretema.rutas.model.poi.Poi;

public class ImageAdapter extends BaseAdapter {

	private static final String	LOG_TAG	= ImageAdapter.class.getSimpleName();

	private Poi					mSelectedPoi;
	private Context				mContext;
	private List<String>		mThumbList;
	private AssetManager		assetManager;
	private Bitmap				mDefaultBitmap;

	public ImageAdapter(final Context c, Poi poi, List<String> mThumbList) {
		mContext = c;
		this.mSelectedPoi = poi;
		this.mThumbList = mThumbList;
		assetManager = mContext.getAssets();
		mDefaultBitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.noimage);
	}

	public final int getCount() {
		return mThumbList.size();
	}

	public final Object getItem(final int position) {
		return position;
	}

	public final long getItemId(final int position) {
		return position;
	}

	public final View getView(final int position, final View convertView, final ViewGroup parent) {
		ImageView i = new ImageView(mContext);

		try {
			Bitmap b= BitmapFactory.decodeStream(assetManager.open(mThumbList.get(position)));
			if(b!=null)
				i.setImageBitmap(b);
			else
				i.setImageBitmap(mDefaultBitmap);
		} catch (IOException e) {
			Log.d(LOG_TAG, "No se pudo abrir el recurso" + mThumbList.get(position));
			i.setImageBitmap(mDefaultBitmap);
		}
		
		i.setScaleType(ImageView.ScaleType.FIT_CENTER);
		
		i.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in = new Intent(mContext, SlideShowActivity.class);
				
				in.putExtra("id_poi", mSelectedPoi.getId());
				in.putExtra("current", position);
				mContext.startActivity(in);
			}
		});
		
		
		return i;
	}

}