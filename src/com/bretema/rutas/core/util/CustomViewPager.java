package com.bretema.rutas.core.util;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CustomViewPager extends ViewPager {
	
	private int childId;
	
	public CustomViewPager(Context context, AttributeSet attrs){
		super(context, attrs); 
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event){
		if(childId > 0){
			View scroll = findViewById(childId);
			
			
		}
		return false;
		
		
	}

}
