package com.example.medicalmanagement.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.medicalmanagement.R;
import com.example.medicalmanagement.helper.CommonMethods;

import java.util.Timer;
import java.util.TimerTask;


public class SplashActivity extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_WRITE_FILES = 102;
    private AlertDialog dialog;
    boolean result;
    ImageView companyLogo;

    public CommonMethods commonMethods;
    public String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_splash);
        commonMethods = new CommonMethods(SplashActivity.this);
        checkAppPermissions();
    }

    public void checkAppPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_NETWORK_STATE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET) && ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA) && ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_NETWORK_STATE)&& ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
                goNext();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.INTERNET,
                                Manifest.permission.CAMERA
                                , Manifest.permission.ACCESS_NETWORK_STATE
                                , Manifest.permission.SEND_SMS
                        },
                        MY_PERMISSIONS_REQUEST_WRITE_FILES);
            }
        } else {
            goNext();
        }
    }

    private void bindLogo() {
        // Start animating the image
        final ImageView splash = (ImageView) findViewById(R.id.companyLogo);
        final AlphaAnimation animation1 = new AlphaAnimation(0.2f, 1.0f);
        animation1.setDuration(700);
        final AlphaAnimation animation2 = new AlphaAnimation(1.0f, 0.2f);
        animation2.setDuration(700);
        //animation1 AnimationListener
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                // start animation2 when animation1 ends (continue)
                splash.startAnimation(animation2);
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationStart(Animation arg0) {
            }
        });

        //animation2 AnimationListener

        animation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                // start animation1 when animation2 ends (repeat)
                splash.startAnimation(animation1);
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationStart(Animation arg0) {
            }
        });

        splash.startAnimation(animation1);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_FILES) {
            Log.e(TAG, "onRequestPermissionsResult: " + grantResults.length);
            int count = 0;
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                goNext();
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
            builder.setMessage("App required some permission please enable it")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                            openPermissionScreen();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            dialog.dismiss();
                            finish();
                        }
                    });
            dialog = builder.show();
        }
    }


    public void goNext() {

        if (commonMethods.isNetworkAvailable(this)) {

        }

        bindLogo();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (!result) {
                    commonMethods.intentFinish(SplashActivity.this, LoginActivity.class);
                }

            }

        };
        // Show splash screen for 3 seconds
        new Timer().schedule(task, 3000);
    }





    public void openPermissionScreen() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", SplashActivity.this.getPackageName(), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

//            public Map<String, String> getHeaders() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/x-www-form-urlencoded");
////                params.put("Authorization", auth);
//                return params;
//            }
        };


