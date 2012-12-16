package com.bretema.rutas.view;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

	private static final String LOG_TAG = ImageAdapter.class.getSimpleName();
	
	private Context			mContext;
	private List<String>	mThumbList;

	private AssetManager			assetManager;
	public ImageAdapter(final Context c, List<String> mThumbList) {
		mContext = c;
		this.mThumbList = mThumbList;
		assetManager = mContext.getAssets();
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
			i.setImageBitmap(BitmapFactory.decodeStream(assetManager
					.open(mThumbList.get(position))));
		} catch (IOException e) {
			Log.d(LOG_TAG,
					"No se pudo abrir el recurso" + mThumbList.get(position));
		}
		//i.setAdjustViewBounds(true);
		// i.setLayoutParams(new
		// Gallery.LayoutParams(LayoutParams.WRAP_CONTENT,
		// LayoutParams.WRAP_CONTENT));
		// i.setBackgroundResource(R.drawable.picture_frame);
		return i;
	}

}