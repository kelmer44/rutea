package com.bretema.rutas.activities;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;
import com.bretema.rutas.view.fragment.InsertCodeDialogFragment;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public abstract class LicensedActivity extends FragmentActivity implements InsertCodeDialogFragment.OnDialogDismissListener{

    private static final String LOG_TAG = LicensedActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();

		actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {       
        startActivity(new Intent(getApplicationContext(),RouteListActivity.class)); 
        return true;
    }
	
    @Override
    public void onDialogDismissListener(boolean auth) {
        Log.d(LOG_TAG, "Authorized: " + auth);
        if(auth){
            launchIntent();
        }
    }
    
    protected abstract void launchIntent();
}
