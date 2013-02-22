package com.bretema.rutas.view.dialog;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bretema.rutas.R;
import com.bretema.rutas.activities.SlideShowActivity;
import com.bretema.rutas.core.util.Constants;
import com.bretema.rutas.model.mapimages.MapImage;

public class MiniGalleryImageAdapter extends BaseAdapter {
	private static final String	LOG_TAG	= MiniGalleryImageAdapter.class.getSimpleName();
	private String				mapId;
	private List<MapImage>		mImages;
	private LayoutInflater		inflater;
	private Context				mContext;

	public MiniGalleryImageAdapter(String mapId, List<MapImage> images, Context c) {
		super();
		this.mapId = mapId;
		this.mImages = images;
		inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContext = c;
	}

	@Override
	public int getCount() {
		return mImages.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView i = new ImageView(mContext);

		Bitmap b = BitmapFactory.decodeFile(Constants.imageMapAssetsPath + mapId + "/" + mImages.get(position).getUri());
		
		if (b != null)
			i.setImageBitmap(b);
		else
			i.setImageResource(R.drawable.noimage);
		

		i.setScaleType(ImageView.ScaleType.FIT_CENTER);

		
		return i;
	}

}
