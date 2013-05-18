package com.bretema.rutas.core;

import android.text.Editable;
import android.text.TextWatcher;

public class CodeInputterTextWatcher implements TextWatcher{

    @Override
    public void afterTextChanged(Editable s) {
        if(s.length()==10){
            s.append("-");
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // TODO Auto-generated method stub
        
    }

}
