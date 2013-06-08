
package com.bretema.rutas.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bretema.rutas.R;
import com.bretema.rutas.core.util.Constants;
import com.bretema.rutas.core.util.MultimediaOrdered;
import com.bretema.rutas.enums.MMType;
import com.bretema.rutas.model.media.Multimedia;
import com.bretema.rutas.model.poi.Poi;
import com.bretema.rutas.service.PoiService;
import com.bretema.rutas.service.impl.PoiServiceImpl;
import com.bretema.rutas.view.fragment.ImageFragment;
import com.bretema.rutas.view.fragment.LabeledImageFragment;
import com.bretema.rutas.view.fragment.TextFragment;
import com.bretema.rutas.view.fragment.VideoFragment;
import com.bretema.rutas.view.fragment.poiInfoFragment;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

import java.util.ArrayList;
import java.util.Collections;

public class SlideShowActivity extends FragmentActivity {
    private static final String LOG_TAG = SlideShowActivity.class.getSimpleName();

    private MyAdapter mAdapter;
    private ViewPager mPager;

    private Integer poiId;

    private Poi currentPoi;
    private PoiService poiService;
    private PageIndicator mIndicator;
    private ArrayList<Multimedia> multimediaList;

    private TextView poiNameTextView;
    private TextView poiDescTextView;
    private TextView textViewMasInfo;
    private Button navigateButton;
    private Button moreInfoButton;
    private ImageButton prevButton;
    private ImageButton nextButton;

    private TextView volverButton;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.slideshow);

        // obtenemos la llamada a esta activity
        Intent i = getIntent();
        // y recogemos el id del poi que nos han pasado
        poiId = i.getIntExtra("id_poi", 0);
        Integer currentItem = i.getIntExtra("current", 0);
        poiService = new PoiServiceImpl(getApplicationContext());

        currentPoi = poiService.getPoi(poiId);

        multimediaList = new ArrayList<Multimedia>();

        multimediaList.addAll(currentPoi.getMedia());
        Collections.sort(multimediaList, new MultimediaOrdered());

        mAdapter = new MyAdapter(getSupportFragmentManager());

        poiNameTextView = (TextView) findViewById(R.id.textViewPOINameSlideShow);
        poiDescTextView = (TextView) findViewById(R.id.textViewPOIDescSlideShow);
        navigateButton = (Button) findViewById(R.id.buttonNavigateSlideShow);
        volverButton = (Button) findViewById(R.id.buttonSlideShowBack);
        moreInfoButton = (Button) findViewById(R.id.buttonMasInfo);
        prevButton = (ImageButton) findViewById(R.id.botonPRev);
        nextButton = (ImageButton) findViewById(R.id.botonNExt);

        poiNameTextView.setText(currentPoi.getNombre());

        Typeface yanone = Typeface.createFromAsset(getAssets(), "YanoneKaffeesatz-Light.ttf");
        Typeface colab = Typeface.createFromAsset(getAssets(), "ColabReg.ttf");
        Typeface colabMed = Typeface.createFromAsset(getAssets(), "ColabMed.ttf");
        poiNameTextView.setTypeface(yanone);
        poiDescTextView.setTypeface(colab);
        poiDescTextView.setText(currentPoi.getDescripcion());

        volverButton.setTypeface(colabMed);
        navigateButton.setTypeface(colabMed);
        moreInfoButton.setTypeface(colabMed);

        volverButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                SlideShowActivity.this.finish();
            }
        });
        navigateButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                
                startActivity(Constants.launchGeoIntent(currentPoi.getNombre(), currentPoi.getLatitude(), currentPoi.getLongitude()));
            }
        });
        moreInfoButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SlideShowActivity.this);
                Multimedia m = multimediaList.get(mPager.getCurrentItem());
                Context mContext = getApplicationContext();
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.dialog_masinfo, null);
                WebView webView = (WebView)layout.findViewById(R.id.webViewDialog);
                webView.loadDataWithBaseURL(null, m.getMas_info(), "text/html", "utf-8", null);
                builder.setView(layout);
                builder.show();

            }
        });

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setCurrentItem(currentItem);
        mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(0);
        
        
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator = indicator;
        indicator.setViewPager(mPager);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            
            @Override
            public void onPageSelected(int page) {
                // TODO Auto-generated method stub
                
                Multimedia m =  multimediaList.get(page);
                
                if(m.getMas_info() == null || m.getMas_info().trim().equals(""))
                {
                    moreInfoButton.setVisibility(View.GONE);
                }
                else
                {
                    moreInfoButton.setVisibility(View.VISIBLE);
                }
            }
            
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
                
            }
        });
        indicator.onPageSelected(0);
        indicator.setSnap(true);

        prevButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mPager.getCurrentItem() > 0) {
                    mPager.setCurrentItem(mPager.getCurrentItem() - 1, true);
                }
            }
        });

        nextButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(mPager.getCurrentItem() + 1, true);
            }
        });

    }

    public class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return multimediaList.size();
        }

        @Override
        public Fragment getItem(int position) {
            Multimedia m = multimediaList.get(position);
            if (m.getTipo() == MMType.Imagen) {
                ImageFragment ifragment = new ImageFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", m.getId());
                ifragment.setArguments(bundle);
                return ifragment;
            }
            else if (m.getTipo() == MMType.Video) {
                VideoFragment ifragment = new VideoFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", m.getId());
                ifragment.setArguments(bundle);
                return ifragment;
            }

            else if (m.getTipo() == MMType.LabeledImage) {
                LabeledImageFragment ifragment = new LabeledImageFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", m.getId());
                ifragment.setArguments(bundle);
                return ifragment;

            }
            else if (m.getTipo() == MMType.Texto) {
                TextFragment ifragment = new TextFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", m.getId());
                ifragment.setArguments(bundle);
                return ifragment;

            }
            else if (m.getTipo() == MMType.MainInfo) {
                poiInfoFragment ifragment = new poiInfoFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", m.getId());
                ifragment.setArguments(bundle);
                return ifragment;
            }
            
         
            return null;
        }

    }

}
