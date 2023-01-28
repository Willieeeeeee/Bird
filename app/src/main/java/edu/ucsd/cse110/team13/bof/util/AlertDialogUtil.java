package edu.ucsd.cse110.team13.bof.util;

import android.app.Activity;
import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatDialogFragment;

public class AlertDialogUtil extends AppCompatDialogFragment {
    public static void showAlert(Activity activity, String title, String message){
        AlertDialog.Builder alertBuilder=new AlertDialog.Builder(activity);
        alertBuilder
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("ok",(dialog,id) -> {dialog.cancel();})
                .setCancelable(true);


        AlertDialog alertDialog=alertBuilder.create();
        alertDialog.show();
    }

}
