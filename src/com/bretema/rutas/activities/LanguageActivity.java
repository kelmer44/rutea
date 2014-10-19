
package com.bretema.rutas.activities;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import java.util.Locale;

public final class LanguageActivity {

    public static final void setLocaleFromPreferences(Context ctx) {

        /** SETEAMOS PRIMERO EL LOCALE O SI NO RESTABLECE EL DEL SISTEMA **/
        String firstLocale = ctx.getResources().getConfiguration().locale.toString().substring(0, 2);
        String currentLocale = PreferenceManager.getDefaultSharedPreferences(ctx).getString("locale", firstLocale);
        /**
         * Locale locale = new Locale((String) currentLocale); config.locale =
         * locale; Locale.setDefault(locale);
         */
        setLocale(ctx, currentLocale);
    }

    public static final void setLocale(Context ctx, String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = ctx.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        // reloadUI(); // you may not need this, in my activity ui must be
        // refreshed immediately so it has a function like this.
    }
}
