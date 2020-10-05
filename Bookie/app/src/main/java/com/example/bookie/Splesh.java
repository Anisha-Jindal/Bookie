package com.example.bookie;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Splesh extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splesh);
        checkAndGo();
    }

    public void checkAndGo() {
        if (isOnline()) {
            int SPLASH_TIME_OUT = 4000;
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    if (LocalSharedPreferences.getIsLogin(Splesh.this)) {
                        Intent i = new Intent(Splesh.this, MainActivity.class);
                        startActivity(i);

                    } else {
                        Intent i = new Intent(Splesh.this, Login.class);
                        startActivity(i);
                    }
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
        else {

            Toast.makeText(this, "Oh no!  No Internet found. Check your connection and try again", Toast.LENGTH_LONG).show();
            AlertDialog alertDialog = new AlertDialog.Builder(Splesh.this).create();
            alertDialog.setTitle("Info");
            alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
          //  alertDialog.setIcon(R.drawable.profile);
            alertDialog.setCanceledOnTouchOutside(false);

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "TRY AGAIN", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "Checking your Internet Connection", Toast.LENGTH_LONG).show();
                    //   mProgressDialog = Utills.showProgressDialog(SplashScreen.this);
                    checkAndGo();
                }
            });

            alertDialog.show();
        }
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = null;
        if (cm != null) {
            netInfo = cm.getActiveNetworkInfo();
        }
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
