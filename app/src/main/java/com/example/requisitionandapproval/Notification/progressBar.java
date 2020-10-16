package com.example.requisitionandapproval.Notification;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.requisitionandapproval.R;



public class progressBar {
    Activity activity;
    AlertDialog dialog;

    public progressBar(Activity activity) {
        this.activity = activity;

    }
    public void StartLoading(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater layoutInflater = activity.getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.progressbar,null));
        builder.setCancelable(false);

        dialog= builder.create();
        dialog.show();
    }

    public void dismissProgress(){

        dialog.dismiss();
    }
}
