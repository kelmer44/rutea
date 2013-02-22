package com.bretema.rutas.view.fragment;

import java.util.List;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bretema.rutas.R;
import com.bretema.rutas.core.util.Constants;
import com.bretema.rutas.model.mapimages.MapImage;
import com.bretema.rutas.service.MapImageService;
import com.bretema.rutas.service.impl.MapImageServiceImpl;
import com.bretema.rutas.view.ImageMap;
import com.bretema.rutas.view.dialog.GalleryDialogFragment;

public class LabeledImageFragment extends MultimediaFragment {
	private static final String	LOG_TAG	= LabeledImageFragment.class.getSimpleName();

	private ImageView			mainImageView;

	private ImageMap			mImageMap;

	private MapImageService		mapImageService;

	private List<MapImage>		areaImages;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.labelimage_fragment, container, false);
		TextView textView = (TextView) view.findViewById(R.id.imageTitleSlideshow);
		textView.setTypeface(Constants.getSubtitleFont(getActivity().getAssets()));

		mImageMap = (ImageMap) view.findViewById(R.id.imageViewMap);
		mImageMap.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				mImageMap.resetExpandSize();
				mImageMap.setImageBitmap(BitmapFactory.decodeFile(Constants.appPath + getMultimedia().getUri()));
				if (getMultimedia().getThumbUri() != null && !getMultimedia().getThumbUri().equals(""))
					mImageMap.loadMapFromAssets(getActivity().getAssets(), getMultimedia().getThumbUri());
				else
					mImageMap.loadMap("none");
				mImageMap.setBalloonTypeface(Constants.getTextFont(getActivity().getAssets()));

			}
		});

		// add a click handler to react when areas are tapped
		mImageMap.addOnImageMapClickedHandler(new ImageMap.OnImageMapClickedHandler() {

			@Override
			public void onImageMapClicked(String id) {
				// when the area is tapped, show the name in a
				// text bubble
				mImageMap.showBubble(id);
				Log.d(LOG_TAG, "Fetching area images...");
				areaImages = mapImageService.getMapImagesByMapAndArea(getMultimedia().getThumbUri(), id);

			}

			@Override
			public void onBubbleClicked(String id) {
				// react to info bubble for area being tapped
				//Toast.makeText(getActivity(), mImageMap.getAreaAttribute(id, "name"), Toast.LENGTH_SHORT).show();
				for (MapImage m : areaImages) {
					Log.d(LOG_TAG, m.getDescripcion());
				}
				if(areaImages.size()>0){
					GalleryDialogFragment d = new GalleryDialogFragment(getMultimedia().getThumbUri(), mImageMap.getAreaAttribute(id, "name"), areaImages);
					d.show(getFragmentManager(), "GALLERY");
				}
			}
		});

		
		textView.setText(getMultimedia().getNombre());

		return view;
	}

	@Override
	public void onPageIsChanged() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Obtenemos el servicio para recoger las imagenes asociadas a los
		// puntos interactivos del mapa;
		// los capturaremos en CreateView ya que es donde se carga el xml
		mapImageService = new MapImageServiceImpl(getActivity().getApplicationContext());
	}
}
