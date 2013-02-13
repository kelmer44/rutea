package com.bretema.rutas.view.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bretema.rutas.R;
import com.bretema.rutas.core.util.Constants;
import com.bretema.rutas.model.imagepoint.ImagePoint;
import com.bretema.rutas.view.ImageMap;

public class LabeledImageFragment extends MultimediaFragment {
	private static final String	LOG_TAG	= LabeledImageFragment.class.getSimpleName();

	private List<ImagePoint>	imagePoints;
	private List<ImageView>		imageViews;
	private ImageView			mainImageView;

	private ImageMap	mImageMap;

	public LabeledImageFragment() {
		super();

		imagePoints = new ArrayList<ImagePoint>();
		imageViews = new ArrayList<ImageView>();
		
		imagePoints.add(new ImagePoint(1860, 95, "Cristosende"));
		imagePoints.add(new ImagePoint(1935, 67, "Castelo de Castro Caldelas"));
		imagePoints.add(new ImagePoint(2027, 99, "San Lourenzo"));
		imagePoints.add(new ImagePoint(1950, 125, "San Adrián: Lugar en el que existió un monasterio"));
		imagePoints.add(new ImagePoint(2114, 137, "Sacardebois"));
		imagePoints.add(new ImagePoint(1992, 214,
				"Según la tradición, descendientes de Noé, llegados hasta Porta Brosmos remontando el río desde el mar, plantaron aquí las primeras viñas de la Ribeira."));
		imagePoints.add(new ImagePoint(1201, 88, "La tradición atribuye numerosos milagros a la Virgen de Cadeiras, que cuenta con gran devoción en la comarca"));
		imagePoints.add(new ImagePoint(699, 74, "Monforte de Lemos"));
		imagePoints.add(new ImagePoint(220, 105, "A Arriba, Parroquia de Barantes (Sober)"));
		imagePoints.add(new ImagePoint(1132, 132, "El desnivel llega en este punto hasta los 450 metros."));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.labelimage_fragment, container, false);
		TextView textView = (TextView) view.findViewById(R.id.imageTitleSlideshow);
		textView.setTypeface(Constants.getSubtitleFont(getActivity().getAssets()));
		
		mImageMap = (ImageMap)view.findViewById(R.id.imageViewMap);
        
        // add a click handler to react when areas are tapped
        mImageMap.addOnImageMapClickedHandler(new ImageMap.OnImageMapClickedHandler() {
			@Override
			public void onImageMapClicked(int id) {
				// when the area is tapped, show the name in a 
				// text bubble
				mImageMap.showBubble(id);
			}

			@Override
			public void onBubbleClicked(int id) {
				// react to info bubble for area being tapped
			}
		});
		
		/*mainImageView = (ImageView) view.findViewById(R.id.imageViewSlideshow);

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(Constants.appPath + getMultimedia().getUri(), options);
		int imageHeight = options.outHeight;
		int imageWidth = options.outWidth;

		Bitmap b = BitmapFactory.decodeFile(Constants.appPath + getMultimedia().getUri());

		ViewTreeObserver vto = mainImageView.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			private int	finalHeight;
			private int	finalWidth;

			public boolean onPreDraw() {

				finalHeight = mainImageView.getMeasuredHeight();
				finalWidth = mainImageView.getMeasuredWidth();

				int iH = mainImageView.getDrawable().getIntrinsicHeight();// original
																			// height of
																			// underlying
																			// image
				int iW = mainImageView.getDrawable().getIntrinsicWidth();// original
																			// width of
																			// underlying
																			// image
				
				for (int i= 0; i< imagePoints.size(); i++) {
					ImagePoint imagePoint = imagePoints.get(i);
					ImageView iview = imageViews.get(i);
					

					RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.labelimage_relativelayout);

					RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
					lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
					
					
					float realx = imagePoint.getX() * finalWidth / 2257;
					float realy = imagePoint.getY() * finalHeight / 553;
					Log.d(LOG_TAG, "orX: " + imagePoint.getX() + " orY: " + imagePoint.getY());
					Log.d(LOG_TAG, "X: " + realx + " Y: " + realy);
					lp.leftMargin = (int) realx;
					lp.topMargin = (int) realy;
					
					iview.setLayoutParams(lp);
					Log.d(LOG_TAG, "Height: " + iH + " Width: " + iW);
				}
				return true;
			}
		});

		for (ImagePoint imagePoint : imagePoints) {
			ImageView imgView = new ImageView(getActivity());

			

			imgView.setImageResource(R.drawable.imagepoint);

			RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.labelimage_relativelayout);

			imageViews.add(imgView);
			rl.addView(imgView);
		}

		// textView.setText(imageCaption);
		if (b != null) {
			float ratio = (float) b.getHeight() / (float) b.getWidth();
			int width = (int) Math.ceil(480 / ratio);

			mainImageView.setImageBitmap(Bitmap.createScaledBitmap(b, width, 480, true));
			b.recycle();
		} else {
			Log.e(LOG_TAG, "Img not found " + getMultimedia().getUri());
			mainImageView.setImageResource(R.drawable.noimage);
		}

*/
		textView.setText(getMultimedia().getNombre());

		return view;
	}

	@Override
	public void onPageIsChanged() {
		// TODO Auto-generated method stub
		
	}
}
