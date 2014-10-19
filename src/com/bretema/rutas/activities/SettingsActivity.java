
package com.bretema.rutas.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

import com.bretema.rutas.R;

import java.util.Locale;

public class SettingsActivity extends PreferenceActivity implements OnPreferenceChangeListener {

    public static final String KEY_LANGUAGE = "locale";
    public static final Integer RESULT_CODE_LANGUAGE = 100;
    private ListPreference language_preference;
    private SharedPreferences sharedPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        language_preference = (ListPreference) findPreference(KEY_LANGUAGE);
        language_preference.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference.getKey().equals("locale")) {

            // Obtenemos editor de preferecias
            Editor ep = sharedPrefs.edit();
            // y ponemos el valor del locale nuevo en las mismas
            ep.putString("locale", (String) newValue);
            ep.commit();

            /** Esto cambia la aplicación al idioma seleccionado **/
            Locale locale = new Locale((String) newValue);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getApplicationContext().getResources().updateConfiguration(config, null);

            // Devolvemos ok como resultado
            setResult(RESULT_CODE_LANGUAGE);
            restartApp();
            //
            return true;
        }
        return true;
    }
    

    public void restartApp() {
        Intent mStartActivity = new Intent(this.getApplicationContext(), SplashActivity.class);
        mStartActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(this.getApplicationContext(), mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) this.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        android.os.Process.killProcess(android.os.Process.myPid());
        finish();
    }
}
