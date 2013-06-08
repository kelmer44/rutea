package com.bretema.rutas.core.util;

import com.bretema.rutas.model.media.Multimedia;

import java.util.Comparator;

public class MultimediaOrdered implements Comparator<Multimedia> {

    @Override
    public int compare(Multimedia lhs, Multimedia rhs) {
        /*
         * compare(a,a) returns zero for all a 
the sign of compare(a,b) must be the opposite of the sign of compare(b,a) for all pairs of (a,b) 
From compare(a,b) > 0 and compare(b,c) > 0 it must follow compare(a,c) > 0 for all possible combinations of (a,b,c) 
         */
        return lhs.getOrden() - rhs.getOrden();
    }

}