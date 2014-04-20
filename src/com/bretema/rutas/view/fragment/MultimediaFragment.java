
package com.bretema.rutas.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.bretema.rutas.model.media.Multimedia;
import com.bretema.rutas.service.MMService;
import com.bretema.rutas.service.impl.MMServiceImpl;

import java.util.Locale;

public abstract class MultimediaFragment extends Fragment {

    private static final String LOG_TAG = MultimediaFragment.class.getSimpleName();

    private MMService multimediaService;
    private Multimedia multimedia;
    private Locale mLocale;
    
    
    public MultimediaFragment()
    {
//        if(this.getArguments().containsKey("locale"))
//        {
//            this.mLocale = (Locale) this.getArguments().get("locale");
//        }
//        else
//        {
//            this.mLocale = new Locale("es");
//        }
        
        
    }
    
    
    protected Locale getLocaleFromMainActivity()
    {
        return this.getActivity().getResources().getConfiguration().locale;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "initing Fragment");
        multimediaService = new MMServiceImpl(getActivity().getApplicationContext());

        int mmId = getArguments().getInt("id");
        multimedia = multimediaService.getMultimedia(mmId);
        Log.d(LOG_TAG, "got Multimedia with id" + multimedia.getId());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public Multimedia getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(Multimedia multimedia) {
        this.multimedia = multimedia;
    }

    public abstract void onPageIsChanged();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        onPageIsChanged();
    }

}
