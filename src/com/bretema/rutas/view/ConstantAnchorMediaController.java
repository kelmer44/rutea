package com.bretema.rutas.view;


import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;

public class ConstantAnchorMediaController extends MediaController
{

    public ConstantAnchorMediaController(Context context, View anchor)
    {
        super(context);
        super.setAnchorView(anchor);
    }

    @Override
    public void setAnchorView(View view)
    {
        // Do nothing
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
    	return false;
    }
}