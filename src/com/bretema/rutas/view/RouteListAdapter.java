package com.bretema.rutas.view;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bretema.rutas.R;
import com.bretema.rutas.model.ruta.Ruta;

public class RouteListAdapter extends BaseAdapter{
	
	private Activity activity;
	
    private static LayoutInflater inflater=null;
    private List<Ruta> routeList;
    
    public RouteListAdapter(Activity a, List<Ruta> list) {
        activity = a;
        routeList=list;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return routeList.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
        
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.routelist_item, null);
        TextView id_ruta = (TextView) vi.findViewById(R.id.id_ruta);
        TextView nombre=(TextView)vi.findViewById(R.id.nombreRouteListLabel);
        TextView descripcion=(TextView)vi.findViewById(R.id.descRouteListLabel);
        Ruta current = routeList.get(position);
        nombre.setText("" + current.getNombre());
        id_ruta.setText(current.getId().toString());
        descripcion.setText(current.getShortDescription());
        return vi;
    }
}
