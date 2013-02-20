package com.bretema.rutas.view.fragment;

import java.util.ArrayList;
import java.util.List;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.labelimage_fragment, container, false);
		TextView textView = (TextView) view.findViewById(R.id.imageTitleSlideshow);
		textView.setTypeface(Constants.getSubtitleFont(getActivity().getAssets()));
		
		mImageMap = (ImageMap)view.findViewById(R.id.imageViewMap);
		mImageMap.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
            	mImageMap.resetExpandSize();
                mImageMap.setImageBitmap(BitmapFactory.decodeFile(Constants.appPath + getMultimedia().getUri()));
                if(getMultimedia().getThumbUri()!= null && !getMultimedia().getThumbUri().equals(""))
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
			}

			@Override
			public void onBubbleClicked(String id) {
				// react to info bubble for area being tapped
				Toast.makeText(getActivity(), mImageMap.getAreaAttribute(id, "name"), Toast.LENGTH_SHORT).show();
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
