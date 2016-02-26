
package com.bretema.rutas.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.bretema.rutas.R;

import java.util.Locale;

/**
 * Creating splash screen for the app
 * 
 * @author Gabriel
 */
public class SplashActivity extends Activity {

    private static final String LOG_TAG = SplashActivity.class.getSimpleName();
    private static final int DELAY_TIME = 2000; // millis
                                                // to
                                                // wait
                                                // before
                                                // starting
                                                // home
                                                // act.
    private boolean loop = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        LanguageActivity.setLocaleFromPreferences(this.getApplicationContext());


        setContentView(R.layout.splash_layout);

        Handler handler = new Handler();

        // insertData();
        Runnable r = new Runnable() {

            @Override
            public void run() {
                // if(hasStorage()){
                loop = false;
                finish();
                // start the home Screen

                Intent intent = new Intent(SplashActivity.this, RouteListActivity.class);
                SplashActivity.this.startActivity(intent);

                // }
            }
        };
        // run a thread after 2 seconds to start the home screen
        handler.postDelayed(r, DELAY_TIME);

    }

    static public boolean hasStorage() {
        // TODO: After fix the bug, add "if (VERBOSE)" before logging errors.
        String state = Environment.getExternalStorageState();
        Log.v(LOG_TAG, "storage state is " + state);

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }

        return false;
    }

    private void insertData() {

        // getApplicationContext().deleteDatabase("rutas.db");
        // // Servicio desde el que abstraemos la base de datos
        // RutaService rutaService = new
        // RutaServiceImpl(getApplicationContext());
        // PoiService poiService = new PoiServiceImpl(getApplicationContext());
        //
        // Ruta ruta = new Ruta("Arte y paisaje en la Ribeira del Sil",
        // "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\nDonec molestie eros et ante aliquet vitae imperdiet odio dictum. Maecenas tempor, mi vitae fermentum posuere, mi nibh malesuada lacus, id dictum purus eros quis nisi. Curabitur vel dolor sem, a iaculis ante. Curabitur nisl dolor, adipiscing at egestas sit amet, adipiscing ut mauris. Aenean quis leo dignissim ante luctus gravida. Sed metus neque, convallis non vulputate sed, blandit nec turpis. Fusce ac lacinia sapien. Morbi nec mauris eget turpis facilisis vulputate nec non urna. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Maecenas auctor pretium leo, at iaculis tortor lacinia nec. Suspendisse nec viverra mi. Nulla imperdiet blandit nisl, sed condimentum elit porta non.",
        // "ruta1/rsa.jpg");
        //
        // ruta.setDuracion("6.5 horas");
        // ruta.setKm(11.3f);
        // ruta.setIntroAudioPath("ruta1/intro.mp3");
        // ruta.setListImagePath("ruta1/title.jpg");
        // ruta.setRouteFile("ruta1/ruta.gpx");
        // ruta.setShortDescription("Descripci�n de esta ruta.");
        //
        // rutaService.save(ruta);
        // Log.d(LOG_TAG, "Introducida ruta " + ruta.getId());
        //
        //
        // Ruta ruta2 = new Ruta("Arte y Vino en la Ribera del Sil",
        // "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\nDonec molestie eros et ante aliquet vitae imperdiet odio dictum. Maecenas tempor, mi vitae fermentum posuere, mi nibh malesuada lacus, id dictum purus eros quis nisi. Curabitur vel dolor sem, a iaculis ante. Curabitur nisl dolor, adipiscing at egestas sit amet, adipiscing ut mauris. Aenean quis leo dignissim ante luctus gravida. Sed metus neque, convallis non vulputate sed, blandit nec turpis. Fusce ac lacinia sapien. Morbi nec mauris eget turpis facilisis vulputate nec non urna. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Maecenas auctor pretium leo, at iaculis tortor lacinia nec. Suspendisse nec viverra mi. Nulla imperdiet blandit nisl, sed condimentum elit porta non.",
        // "ruta2/rsa.jpg","Descripci�n de esta ruta", "ruta2/ruta.gpx",
        // "ruta2/title.jpg", "ruta2/intro.mp3", "5 horas", 8.3f);
        // rutaService.save(ruta2);
        // Log.d(LOG_TAG, "Introducida ruta " + ruta2.getId());
        //
        // Ruta ruta3 = new Ruta("Los paisajes de la viticultura heroica",
        // "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\nDonec molestie eros et ante aliquet vitae imperdiet odio dictum. Maecenas tempor, mi vitae fermentum posuere, mi nibh malesuada lacus, id dictum purus eros quis nisi. Curabitur vel dolor sem, a iaculis ante. Curabitur nisl dolor, adipiscing at egestas sit amet, adipiscing ut mauris. Aenean quis leo dignissim ante luctus gravida. Sed metus neque, convallis non vulputate sed, blandit nec turpis. Fusce ac lacinia sapien. Morbi nec mauris eget turpis facilisis vulputate nec non urna. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Maecenas auctor pretium leo, at iaculis tortor lacinia nec. Suspendisse nec viverra mi. Nulla imperdiet blandit nisl, sed condimentum elit porta non.",
        // "ruta3/rsa.jpg","Descripci�n de esta ruta", "ruta3/ruta.gpx",
        // "ruta3/title.jpg", "ruta3/intro.mp3", "4 horas", 6.3f);
        // rutaService.save(ruta3);
        // Log.d(LOG_TAG, "Introducida ruta " + ruta3.getId());
        //
        // Ruta ruta4 = new Ruta("Monforte de Lemos y la ribeira de Amandi",
        // "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\nDonec molestie eros et ante aliquet vitae imperdiet odio dictum. Maecenas tempor, mi vitae fermentum posuere, mi nibh malesuada lacus, id dictum purus eros quis nisi. Curabitur vel dolor sem, a iaculis ante. Curabitur nisl dolor, adipiscing at egestas sit amet, adipiscing ut mauris. Aenean quis leo dignissim ante luctus gravida. Sed metus neque, convallis non vulputate sed, blandit nec turpis. Fusce ac lacinia sapien. Morbi nec mauris eget turpis facilisis vulputate nec non urna. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Maecenas auctor pretium leo, at iaculis tortor lacinia nec. Suspendisse nec viverra mi. Nulla imperdiet blandit nisl, sed condimentum elit porta non.",
        // "ruta4/rsa.jpg","Descripci�n de esta ruta", "ruta4/ruta.gpx",
        // "ruta4/title.jpg", "ruta4/intro.mp3", "4.5 horas", 5.3f);
        // rutaService.save(ruta4);
        // Log.d(LOG_TAG, "Introducida ruta " + ruta4.getId());
        //
        // Ruta ruta5 = new Ruta("El rom�nico en la Ribeira de Lugo",
        // "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\nDonec molestie eros et ante aliquet vitae imperdiet odio dictum. Maecenas tempor, mi vitae fermentum posuere, mi nibh malesuada lacus, id dictum purus eros quis nisi. Curabitur vel dolor sem, a iaculis ante. Curabitur nisl dolor, adipiscing at egestas sit amet, adipiscing ut mauris. Aenean quis leo dignissim ante luctus gravida. Sed metus neque, convallis non vulputate sed, blandit nec turpis. Fusce ac lacinia sapien. Morbi nec mauris eget turpis facilisis vulputate nec non urna. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Maecenas auctor pretium leo, at iaculis tortor lacinia nec. Suspendisse nec viverra mi. Nulla imperdiet blandit nisl, sed condimentum elit porta non.",
        // "ruta5/rsa.jpg","Descripci�n de esta ruta", "ruta5/ruta.gpx",
        // "ruta5/title.jpg", "ruta5/intro.mp3", "7 horas", 26.3f);
        // rutaService.save(ruta5);
        // Log.d(LOG_TAG, "Introducida ruta " + ruta5.getId());
        //
        // Ruta ruta6 = new Ruta("La Ribeira Sacra del Mi�o",
        // "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\nDonec molestie eros et ante aliquet vitae imperdiet odio dictum. Maecenas tempor, mi vitae fermentum posuere, mi nibh malesuada lacus, id dictum purus eros quis nisi. Curabitur vel dolor sem, a iaculis ante. Curabitur nisl dolor, adipiscing at egestas sit amet, adipiscing ut mauris. Aenean quis leo dignissim ante luctus gravida. Sed metus neque, convallis non vulputate sed, blandit nec turpis. Fusce ac lacinia sapien. Morbi nec mauris eget turpis facilisis vulputate nec non urna. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Maecenas auctor pretium leo, at iaculis tortor lacinia nec. Suspendisse nec viverra mi. Nulla imperdiet blandit nisl, sed condimentum elit porta non.",
        // "ruta6/rsa.jpg","Descripci�n de esta ruta", "ruta6/ruta.gpx",
        // "ruta6/title.jpg", "ruta6/intro.mp3", "3.5 horas", 3.3f);
        // rutaService.save(ruta6);
        // Log.d(LOG_TAG, "Introducida ruta " + ruta6.getId());
        //
        //
        // Poi poi
    }

}
