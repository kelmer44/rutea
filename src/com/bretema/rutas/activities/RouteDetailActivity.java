
package com.bretema.rutas.activities;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bretema.rutas.R;
import com.bretema.rutas.core.CodeInputterTextWatcher;
import com.bretema.rutas.core.LicenseManager;
import com.bretema.rutas.core.exception.CodeAlreadyUsedException;
import com.bretema.rutas.core.exception.InvalidCodeException;
import com.bretema.rutas.core.util.Constants;
import com.bretema.rutas.model.codigo.Codigo;
import com.bretema.rutas.model.ruta.Ruta;
import com.bretema.rutas.service.RutaService;
import com.bretema.rutas.service.impl.RutaServiceImpl;

/**
 * Detalles de la ruta, pantalla de bienvenida antes de mostrar la ruta en
 * pantalla.
 * 
 * @author kelmer
 */
public class RouteDetailActivity extends LicensedActivity implements MediaPlayer.OnPreparedListener {

    private static final String LOG_TAG = RouteDetailActivity.class.getSimpleName();

    // Servicio desde el que abstraemos la base de datos
    private RutaService rutaService;

    // Id de la ruta actual
    private String id_ruta;

    // ruta con la que trabajamos
    private Ruta ruta;

    // UI elements
    private Button gotoRouteButton;
    private Button backButton;
    private ImageView mainRouteImage;
    private TextView routeDetailNameLabel;
    private TextView routeDetailDescriptionLabel;
    private TextView duracionLabel;
    private TextView distanciaLabel;
    private TextView duracionPlaceholder;
    private TextView distanciaPlaceholder;

    private MediaPlayer mediaPlayer;
    private ToggleButton playButton;
    private ImageView stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_route_detail);

        // obtenemos la llamada a esta activity
        Intent i = getIntent();
        // y recogemos el id de la rutaa que nos han pasado
        id_ruta = i.getStringExtra("id_ruta");

        // Acceso a db
        Log.d(LOG_TAG, "Obteniendo datos de ruta con id" + id_ruta + "...");
        rutaService = new RutaServiceImpl(getApplicationContext());
        ruta = rutaService.getRuta(Integer.parseInt(id_ruta));

        // Obtencion de los elementos del UI
        gotoRouteButton = (Button) findViewById(R.id.gotoRouteButton);
        backButton = (Button) findViewById(R.id.backButtonDetail);
        routeDetailDescriptionLabel = (TextView) findViewById(R.id.routeDetailDescriptionLabel);
        routeDetailDescriptionLabel.setMovementMethod(new ScrollingMovementMethod());
        routeDetailNameLabel = (TextView) findViewById(R.id.routeDetailNameLabel);
        mainRouteImage = (ImageView) findViewById(R.id.mainRouteImage);

        duracionLabel = (TextView) findViewById(R.id.duracionLabel);
        distanciaLabel = (TextView) findViewById(R.id.distanciaLabel);
        duracionPlaceholder = (TextView) findViewById(R.id.duracionPlaceholder);
        distanciaPlaceholder = (TextView) findViewById(R.id.distanciaPlaceholder2);

        playButton = (ToggleButton) findViewById(R.id.playButton);
        stopButton = (ImageView) findViewById(R.id.stopButton);

        // Asignaci�n de valores
        routeDetailDescriptionLabel.setText(Html.fromHtml(ruta.getDescription()));
        routeDetailNameLabel.setText(ruta.getNombre());
        routeDetailNameLabel.setTypeface(Constants.getTitleFont(getAssets()));

        Bitmap b = BitmapFactory.decodeFile(Constants.appPath + ruta.getMainImagePath());
        if (b != null) {
            mainRouteImage.setImageBitmap(b);
        } else {
            mainRouteImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.noimage));
        }

        routeDetailDescriptionLabel.setTypeface(Constants.getTextFont(getAssets()));
        gotoRouteButton.setTypeface(Constants.getSubtitleFont(getAssets()));
        backButton.setTypeface(Constants.getSubtitleFont(getAssets()));
        duracionLabel.setTypeface(Constants.getSubtitleFont(getAssets()));
        distanciaLabel.setTypeface(Constants.getSubtitleFont(getAssets()));
        duracionPlaceholder.setTypeface(Constants.getSubtitleFont(getAssets()));
        distanciaPlaceholder.setTypeface(Constants.getSubtitleFont(getAssets()));

        duracionPlaceholder.setText(ruta.getDuracion());
        distanciaPlaceholder.setText(ruta.getKm() + " km");

        gotoRouteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loadRouteMapActivity();
            }
        });

        playButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (playButton.isChecked()) {
                    mediaPlayer.start();
                } else {
                    mediaPlayer.pause();
                }
            }
        });

        backButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                RouteDetailActivity.this.finish();
            }
        });

        stopButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
                playButton.setChecked(false);
            }
        });
    }

    protected void loadRouteMapActivity() {

        
         LicenseManager lManager = LicenseManager.getInstance();
         //comprobamos si está autorizado
         boolean authorized = lManager.isCurrentlyAuthorized();
         
         //si no lo está, pedimos código
         if(!authorized) {
             AlertDialog.Builder alert = new AlertDialog.Builder(RouteDetailActivity.this);
             
             alert.setTitle(getString(R.string.activation_code));
             alert.setMessage(getString(R.string.enter_activation_code));
        
             // Set an EditText view to get user input
             final EditText input = new EditText(RouteDetailActivity.this);
             input.setInputType(InputType.TYPE_CLASS_PHONE);
        
        
             input.addTextChangedListener(new CodeInputterTextWatcher());
             alert.setView(input);
        
             alert.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int whichButton) {
                     String value = input.getText().toString();
                     try {
                         LicenseManager lManager = LicenseManager.getInstance();
                         boolean validCode = lManager.checkLicense(value);
                         if(validCode){
                             //guardamos
                             Codigo code = lManager.saveCode(value);
                             if(code!=null){
                                 //autorizamos
                                 lManager.auth(value);
                                 launchIntent();
                             }
                             else
                             {
                                 Log.d(LOG_TAG, "Error al guardar codigo");
                             }
                         }
                         Log.d(LOG_TAG, "Código correcto");
                     } catch (InvalidCodeException e) {
                         Log.d(LOG_TAG, "Invalid code " + e.getCode());
                         AlertDialog.Builder invalidCodeDialog = new AlertDialog.Builder(RouteDetailActivity.this);
                         invalidCodeDialog.setMessage(getString(R.string.invalid_code));
                     } catch (CodeAlreadyUsedException e) {
                         Log.d(LOG_TAG, "This code was already used: " + e.getCode());
                         AlertDialog.Builder codeUsedDialog = new AlertDialog.Builder(RouteDetailActivity.this);
                         codeUsedDialog.setMessage(getString(R.string.code_already_used));
                     }
                 }
             });
        
             alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int whichButton) {
                     AlertDialog.Builder codeRequiredDialog = new AlertDialog.Builder(RouteDetailActivity.this);
                     codeRequiredDialog.setMessage(getString(R.string.valid_code_required));
                 }
             });
        
             alert.show();
         }
         else
         {
             launchIntent();
         }
        

    }

    private void launchIntent() {
        Intent in = new Intent(getApplicationContext(), RouteMapActivity.class);
        in.putExtra("id_ruta", id_ruta);
        startActivity(in);
    }

    private void loadAudioFile() {

        Log.d(LOG_TAG, "Trying to load audio file...");
        try {
            mediaPlayer.setDataSource(Constants.appPath + ruta.getIntroAudioPath());
            mediaPlayer.prepareAsync();
        } catch (IOException e1) {
            Log.e(LOG_TAG, "Could not open file " + ruta.getIntroAudioPath() + " for playback.", e1);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_route_detail, menu);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();

        // deallocate all memory
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);

        playButton.setChecked(false);
        loadAudioFile();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d(LOG_TAG, "onPrepared ready");
        // mediaPlayer.start();
        playButton.setEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_info:
                Intent in = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(in);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
