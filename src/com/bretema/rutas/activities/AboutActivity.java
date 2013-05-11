
package com.bretema.rutas.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bretema.rutas.R;
import com.bretema.rutas.core.util.Constants;

public class AboutActivity extends LicensedActivity {

	private long crc32address;
	private String address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_about);

		TextView textViewAppName = (TextView) findViewById(R.id.textViewAppName);
		TextView textViewAppDesc = (TextView) findViewById(R.id.textViewAppDesc);
		TextView textViewSysInfo = (TextView) findViewById(R.id.sysInfo);
		ImageView bretemaLogo = (ImageView) findViewById(R.id.bretemaLogo);

		bretemaLogo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(AboutActivity.this);

				alert.setTitle("Title");
				alert.setMessage("Message");

				// Set an EditText view to get user input
				final EditText input = new EditText(AboutActivity.this);
				alert.setView(input);

				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String value = input.getText().toString();
						String[] codes = value.split("-");
						try{
							long checkCode = Long.parseLong(codes[0].trim());
							long encodedMac = Long.parseLong(codes[1].trim());
							long result = encodedMac-checkCode;
							
							if(result == crc32address){
								Toast.makeText(AboutActivity.this, "Codigo correcto!", Toast.LENGTH_LONG);
							}
							else
							{
								Toast.makeText(AboutActivity.this, "Codigo incorrecto!", Toast.LENGTH_LONG);
							}
							
						}catch(NumberFormatException nfe)
						{
							Toast.makeText(AboutActivity.this, "Clave incorrecta!", Toast.LENGTH_SHORT).show();
						}
						
					}
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});

				alert.show();
			}
		});

		textViewAppName.setTypeface(Constants.getSubtitleFont(getAssets()));
		textViewAppDesc.setTypeface(Constants.getSubtitleFont(getAssets()));
		textViewSysInfo.setTypeface(Constants.getSubtitleFont(getAssets()));

		WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = manager.getConnectionInfo();
		address = info.getMacAddress();
		crc32address = Constants.calculateCrc32(address.toUpperCase());

		StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
		double sdAvailSize = (double) stat.getAvailableBlocks()
				* (double) stat.getBlockSize();
		// One binary gigabyte equals 1,073,741,824 bytes.
		double gigaAvailable = sdAvailSize / 1073741824;
		String versionName;
		try {
			versionName = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			versionName = "N/A";
		}
		String sysinfo = "Versión:" + versionName + "\nEspacio libre: " + gigaAvailable + " Gb \nMAC address: " + address + "(" + crc32address + ")";

		textViewSysInfo.setText(sysinfo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_about, menu);
		return true;
	}
	


}
