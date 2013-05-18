package com.bretema.rutas.core;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.bretema.rutas.R;
import com.bretema.rutas.core.exception.CodeAlreadyUsedException;
import com.bretema.rutas.core.exception.InvalidCodeException;
import com.bretema.rutas.core.util.Constants;
import com.bretema.rutas.model.codigo.Codigo;
import com.bretema.rutas.service.CodigoService;
import com.bretema.rutas.service.impl.CodigoServiceImpl;
import com.bretema.rutas.view.PromptDialog;

public class LicenseManager {
    private final static String LOG_TAG = LicenseManager.class.getName();
    
    
    private Context mContext;
    private CodigoService codigoService;
    
    private String macAddress;
    private long hashedMacAddress;
    private long crc32address;
    
    private static LicenseManager INSTANCE = null;
    
    private LicenseManager()
    {
        
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
        mContext = context;
        macAddress = obtainMacAddress();
        crc32address = Constants.calculateCrc32(macAddress.toUpperCase());
        codigoService = new CodigoServiceImpl(mContext);
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
