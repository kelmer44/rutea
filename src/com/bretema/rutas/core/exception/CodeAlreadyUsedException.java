package com.bretema.rutas.core.exception;

import android.content.Context;

import com.bretema.rutas.R;

public class CodeAlreadyUsedException extends AndroidException{
    String code = "";
    public CodeAlreadyUsedException(String code, Context context)
    {
        super(context);
        this.code = code;
    }
    @Override
    public String getMessage() {
        return getContext().getResources().getString(R.string.code_already_used);
    }
    public String getCode() {
        return code;
    }
    
    
}
