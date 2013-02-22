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
import android.widget.Gallery;

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
