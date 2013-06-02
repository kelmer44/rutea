package com.bretema.rutas.view.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;

import com.bretema.rutas.R;
import com.bretema.rutas.core.CodeInputterTextWatcher;
import com.bretema.rutas.core.LicenseManager;
import com.bretema.rutas.core.exception.CodeAlreadyUsedException;
import com.bretema.rutas.core.exception.InvalidCodeException;
import com.bretema.rutas.model.codigo.Codigo;

public class InsertCodeDialogFragment extends DialogFragment {
    public static final String LOG_TAG = InsertCodeDialogFragment.class.getName();
    private boolean authorized = false;
    
    OnDialogDismissListener mCallback;

    
    public interface OnDialogDismissListener {
        public void onDialogDismissListener(boolean auth);
    }
    
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        
        //This makes sure the container activity has implemented
        //the callback interface. If not, throws an exception
        try {
            mCallback = (OnDialogDismissListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnDialogDismissListener");
        }
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
     // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.activation_code);
        builder.setMessage(R.string.enter_activation_code);
        
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        input.addTextChangedListener(new CodeInputterTextWatcher());
        
        builder.setView(input);
        
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
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
                                   setAuthorized(true);
                                   
                               }
                               else
                               {
                                   
                                   Log.d(LOG_TAG, "Error al guardar codigo");
                               }
                           }
                           Log.d(LOG_TAG, "Código correcto");
                       } catch (InvalidCodeException e) {
                           Log.d(LOG_TAG, "Invalid code " + e.getCode());
                           AlertDialog.Builder invalidCodeDialog = new AlertDialog.Builder(getActivity());
                           invalidCodeDialog.setMessage(String.format(getString(R.string.invalid_code), value));
                           invalidCodeDialog.setPositiveButton(R.string.ok, null);
                           invalidCodeDialog.show();
                       } catch (CodeAlreadyUsedException e) {
                           Log.d(LOG_TAG, "This code was already used: " + e.getCode());
                           AlertDialog.Builder codeUsedDialog = new AlertDialog.Builder(getActivity());
                           codeUsedDialog.setMessage(String.format(getString(R.string.code_already_used), value));
                           codeUsedDialog.setPositiveButton(R.string.ok, null);
                           codeUsedDialog.show();
                       }
                       mCallback.onDialogDismissListener(isAuthorized());
                   }
               });
        
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       AlertDialog.Builder codeRequiredDialog = new AlertDialog.Builder(getActivity());
                       codeRequiredDialog.setMessage(getString(R.string.valid_code_required));
                       codeRequiredDialog.setPositiveButton(R.string.ok, null);
                       codeRequiredDialog.show();

                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }
    
    
    
    
}
