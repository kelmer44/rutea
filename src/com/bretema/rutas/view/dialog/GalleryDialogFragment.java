package com.bretema.rutas.view.dialog;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.TextView;

import com.bretema.rutas.R;
import com.bretema.rutas.model.mapimages.MapImage;

public class GalleryDialogFragment extends DialogFragment {
	private String 			id;
	private String			title;
	private List<MapImage>	mapImages;

	public GalleryDialogFragment(String id, String title, List<MapImage> mapImages) {
		super();
		this.id = id;
		this.title = title;
		this.mapImages = mapImages;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.CustomDialogTheme));
		
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.mini_gallery, null);
		Gallery gallery = (Gallery)view.findViewById(R.id.mapImageGallery);
		gallery.setAdapter(new MiniGalleryImageAdapter(id, mapImages, getActivity().getApplicationContext()));
		final TextView text = (TextView)view.findViewById(R.id.imageCaptionMiniGallery);
		
		gallery.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if(text.getVisibility()== View.GONE)
					text.setVisibility(View.VISIBLE);
				else
					text.setVisibility(View.GONE);
			}
		});
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				text.setText(mapImages.get(position).getDescripcion());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				text.setVisibility(View.GONE);
			}
		});
		//builder.setMessage(mapImages.get(0).getDescripcion());
		builder.setTitle(title);
		builder.setView(view);

		builder.setNeutralButton("Volver", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});
		// 3. Get the AlertDialog from create()
		AlertDialog dialog = builder.create();
		return dialog;
	}

}
