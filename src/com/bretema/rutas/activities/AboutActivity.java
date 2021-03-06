
package com.bretema.rutas.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bretema.rutas.R;
import com.bretema.rutas.core.util.Constants;
import com.bretema.rutas.service.CodigoService;
import com.bretema.rutas.service.impl.CodigoServiceImpl;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AboutActivity extends Activity {

    private static final String LOG_TAG = AboutActivity.class.getName();
    private long crc32address;
    private String address;
    private CodigoService codigoService;
    public static final int SETTINGS_REQUEST = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_about);

        TextView textViewAppName = (TextView) findViewById(R.id.textViewAppName);
        TextView textViewAppDesc = (TextView) findViewById(R.id.textViewAppDesc);
        TextView textViewSysInfo = (TextView) findViewById(R.id.sysInfo);
        ImageView bretemaLogo = (ImageView) findViewById(R.id.bretemaLogo);
        ImageView ruteaLogo = (ImageView) findViewById(R.id.info_blue);
        codigoService = new CodigoServiceImpl(getApplicationContext());

        bretemaLogo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bretemasoftware.com"));
                startActivity(browserIntent);
            }
        });

        ruteaLogo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.rutea.es"));
                startActivity(browserIntent);
            }
        });

        textViewAppName.setTypeface(Constants.getSubtitleFont(getAssets()));
        textViewAppDesc.setTypeface(Constants.getSubtitleFont(getAssets()));
        textViewSysInfo.setTypeface(Constants.getSubtitleFont(getAssets()));

        WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        address = info.getMacAddress();
        if (address == null) {
            address = "bc:0f:2b:a5:32:97";
        }
        crc32address = Constants.calculateCrc32(address.toUpperCase());

        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        double sdAvailSize = (double) stat.getAvailableBlocks()
                * (double) stat.getBlockSize();
        // One binary gigabyte equals 1,073,741,824 bytes.
        double gigaAvailable = sdAvailSize / 1073741824;
        String versionName;
        try {
            versionName = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            versionName = "N/A";
        }
        String sysinfo = getResources().getString(R.string.version) + ": " + versionName + "\n" + getResources().getString(R.string.free_space) + ": " + String.format("%1$,.2f", gigaAvailable) + " Gb \nMAC address: " + address;

        textViewSysInfo.setText(sysinfo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_about, menu);
        return true;
    }

    // This method is called once the menu is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                // Launch settings activity
                Intent i = new Intent(this, SettingsActivity.class);
                startActivityForResult(i, SETTINGS_REQUEST);
                break;
        // more code...
        }
        return true;
    }

    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent
            data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SETTINGS_REQUEST:
                // Esto significa que se ha cambiado el idioma (posiblemente la
                // accesibilidad también
                if (resultCode == SettingsActivity.RESULT_CODE_LANGUAGE) {
                    
                }

                break;
        }

    }

}
