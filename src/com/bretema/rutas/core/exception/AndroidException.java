package com.bretema.rutas.core.exception;

import android.content.Context;

import com.bretema.rutas.R;

public abstract class AndroidException extends Exception {
    private Context context = null;
    public AndroidException(Context context)
    {
        context = context;
    }
    

    protected Context getContext() {
        return context;
    }
}
