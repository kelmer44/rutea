
package com.bretema.rutas.core;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.bretema.rutas.core.util.Constants;

public class LicenseManagerService extends Service {

    private final static String LOG_TAG = LicenseManagerService.class.getName();
    private boolean isActivated = false;
    private String lastInputCode = "";
    private String currentValidCode = "";

    private String macAddress;
    private long hashedMacAddress;

    @Override
    public void onCreate() {
        Log.d(LOG_TAG, "LicenseManagerService.onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        Log.d(LOG_TAG, "LicenseManagerService.onStart()");

        // TODO do something useful
        WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        macAddress = info.getMacAddress();
        hashedMacAddress = Constants.calculateCrc32(macAddress.toUpperCase());

        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "LicenseManagerService.onBind()");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(LOG_TAG, "LicenseManagerService.onUnbind()");
        return super.onUnbind(intent);

    }

    public String getMacAddress() {
        return macAddress;
    }

    public long getHashedMacAddress() {
        return hashedMacAddress;
    }

}
