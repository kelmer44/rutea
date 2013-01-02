package com.bretema.rutas.view;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bretema.rutas.R;
import com.bretema.rutas.model.ruta.Ruta;

public class ImageGridAdapter extends BaseAdapter {
	private static final String	LOG_TAG	= ImageGridAdapter.class.getSimpleName();

    private static LayoutInflater inflater=null;
	private Context				mContext;
    private List<Ruta> routeList;

	public ImageGridAdapter(final Context c,List<Ruta> list) {
		mContext = c;
		routeList = list;
        inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		
		View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.grid_item, null);
        
        LinearLayout linLayout = (LinearLayout) vi.findViewById(R.id.linearlayoutGridLabel);
        Typeface yanone = Typeface.createFromAsset(mContext.getAssets(), "YanoneKaffeesatz-Regular.ttf");
        TextView id_ruta = (TextView) vi.findViewById(R.id.id_ruta);
        TextView nombre=(TextView)vi.findViewById(R.id.routenameGridLabel);
        nombre.setTypeface(yanone);
        Ruta current = routeList.get(position);
		try {
	        Drawable d;
			d = Drawable.createFromStream(mContext.getAssets().open("ruta" + (position+1) + "/title.jpg"), null);
			/*Bitmap b = BitmapFactory.decodeStream(mContext.getAssets().open("ruta" + (position+1) + "/title.jpg"));
			StreamDrawable d = new StreamDrawable(b, 10, 0);*/
	        linLayout.setBackgroundDrawable(d);
			
			/*LayoutParams lp = linLayout.getLayoutParams();
			lp.width = mContext.getResources().getDisplayMetrics().widthPixels;
			lp.height = (int) (lp.width / ratio);*/
			
		} catch (IOException e) {
			linLayout.setBackgroundResource(R.drawable.noimage);
		}

        nombre.setText("" + current.getNombre());
        id_ruta.setText(current.getId().toString());
        
        return vi;
	}

}
