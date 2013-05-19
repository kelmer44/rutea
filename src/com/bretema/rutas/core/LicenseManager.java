package com.bretema.rutas.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.util.Log;

import com.bretema.rutas.core.exception.CodeAlreadyUsedException;
import com.bretema.rutas.core.exception.InvalidCodeException;
import com.bretema.rutas.core.util.Constants;
import com.bretema.rutas.model.codigo.Codigo;
import com.bretema.rutas.service.CodigoService;
import com.bretema.rutas.service.impl.CodigoServiceImpl;

import java.util.Date;

public class LicenseManager {
    private final static String LOG_TAG = LicenseManager.class.getName();
    
    
    private Context mContext;
    private CodigoService codigoService;
    
    private String macAddress;
    private long crc32address;
    
    private SharedPreferences sharedPreferences;
    
    private static LicenseManager INSTANCE = null;
    
    private LicenseManager()
    {
        
    }

    /**
     * checks if the application is currently in an authorized state from the shared preferences
     * First checks the shared preferences, then for the current code checks dates
     * @return
     */
    public boolean isCurrentlyAuthorized()
    {
        boolean isAuthorized = sharedPreferences.getBoolean("auth", false);
        
        if(isAuthorized)
        {
            String code = sharedPreferences.getString("code", "");
            if(code.equals(""))
                return false;
            
            Codigo currentCode = codigoService.getCodigoByCodigo(code);
            
            if(currentCode==null){
                deAuth();
                return false;
            }
            
            Date deactivationDate = currentCode.getDeactivationDate();
            Date hoy = new Date();
            if(deactivationDate.before(hoy)){
                deAuth();
                return false;
            }
        }
        
        return isAuthorized;
    }
    
    public void deAuth() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("auth", false);
        editor.putString("code", "");
    }
    
    public void auth(String code){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("auth", true);
        editor.putString("code", code);
    }

    private synchronized static void createInstance()
    {
        if(INSTANCE == null)
        {
            INSTANCE = new LicenseManager();
        }
    }
    
    public static LicenseManager getInstance()
    {
        createInstance();
        return INSTANCE;
    }
    
    public void init(Context context)
    {
        Log.d(LOG_TAG, "Iniciando License Manager...");
        //grabs the context
        mContext = context;
        
        //gets macAddress from context
        macAddress = obtainMacAddress();
        crc32address = Constants.calculateCrc32(macAddress.toUpperCase());
        //grabs service to store codes in database
        codigoService = new CodigoServiceImpl(mContext);
        
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        
        Log.d(LOG_TAG, "Done.");
    }
   
    public boolean checkLicense(String inputCode) throws InvalidCodeException, CodeAlreadyUsedException
    {
        Log.d(LOG_TAG,"Checking code " + inputCode);
        String[] codes = inputCode.split("-");
        try{
            long checkCode = Long.parseLong(codes[0].trim());
            long encodedMac = Long.parseLong(codes[1].trim());
            long result = encodedMac-checkCode;
            
            if(result == crc32address){
                if(!codigoService.codeUsed(inputCode))
                {
                    Log.d(LOG_TAG,"Code was valid. Storing in database");
                    Codigo c = codigoService.save(inputCode);
                    return true;
                }
                else {
                    Log.d(LOG_TAG,"Code was already used");
                    throw new CodeAlreadyUsedException(inputCode, mContext);
                }
                
            }
            else
            {
                Log.d(LOG_TAG,"Code was not valid");
                throw new InvalidCodeException(inputCode, mContext);
            }
        }catch(NumberFormatException nfe)
        {
            Log.d(LOG_TAG,"Code was not numeric");
            throw new InvalidCodeException(inputCode, mContext);
        }
    }

    private String obtainMacAddress() {
        WifiManager manager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getMacAddress();
        if(address==null){
            address = "bc:0f:2b:a5:32:97";
        }
        return address;
    }
}
