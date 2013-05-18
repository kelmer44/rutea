package com.bretema.rutas.core.exception;

import android.content.Context;

import com.bretema.rutas.R;

public class InvalidCodeException extends AndroidException{

    String code = "";
    public InvalidCodeException(String code, Context context)
    {
        super(context);
        this.code = code;
        if(code==null)
            code = "";
    }
    public String getCode() {
        return code;
    }
    
    
}
