package com.bretema.rutas.view;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bretema.rutas.R;
import com.bretema.rutas.core.util.Constants;
import com.bretema.rutas.model.ruta.Ruta;

public class ImageGridAdapter extends BaseAdapter {
	private static final String		LOG_TAG		= ImageGridAdapter.class.getSimpleName();

	private static LayoutInflater	inflater	= null;
	private Context					mContext;
	private List<Ruta>				routeList;

	public ImageGridAdapter(final Context c, List<Ruta> list) {
		mContext = c;
		routeList = list;
		inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return routeList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.grid_item, parent, false);
		}

		LinearLayout linLayout = (LinearLayout) vi.findViewById(R.id.linearlayoutGridLabel);
		TextView id_ruta = (TextView) vi.findViewById(R.id.id_ruta);
		TextView nombre = (TextView) vi.findViewById(R.id.routenameGridLabel);
		nombre.setTypeface(Constants.getTitleFontRegular(mContext.getAssets()));
		Ruta current = routeList.get(position);
		/*
		 * Drawable d; d =
		 * Drawable.createFromStream(mContext.getAssets().open("ruta" +
		 * (position + 1) + "/title.jpg"), null);
		 */

		//Coger de la SD
		//Bitmap b = BitmapFactory.decodeFile(Constants.APP_PATH + current.getListImagePath());
		
		//Coger de la app
		AssetManager mngr = mContext.getAssets();
		InputStream is;
		Bitmap b = null;
        try {
            is = mngr.open(current.getListImagePath());
             b = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }      
        
        
        
		if (b != null) {
			StreamDrawable d = new StreamDrawable(b, 10, 0);
			linLayout.setBackgroundDrawable(d);
		} else {
			linLayout.setBackgroundResource(R.drawable.imagenenblanco);
		}
		
		nombre.setText("" + current.getNombre());
		id_ruta.setText(current.getId().toString());

		return vi;
	}

}
