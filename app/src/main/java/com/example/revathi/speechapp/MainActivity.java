package com.example.revathi.speechapp;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public final int RESULT_SPEECH = 101;
    private int MY_PERMISSIONS_RECORD_AUDIO = 102;
    private Context clContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clContext = this;
        setContentView(R.layout.activity_main);
    }

    public void onGoogleVrBtnClick(View view) {

        if (!Utili.isAirplaneModeOn(clContext)) {
            /*
             * here we are calling RecognizerIntent class which given android class for example 1
             * */
            if (Utili.isWifiNetWorkEnabled(clContext) && Utili.isOnline()) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(), "Opps! Your device doesn’t support Speech to Text", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(clContext, "Check your Internet connection and Try Again!..", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(clContext, "Turn off Airplane mode and Try Again!..", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    Toast.makeText(clContext, "You Said : " + text.get(0).toString(), Toast.LENGTH_SHORT).show();

                }
                break;
            }

        }
    }

    public void onCustomVrBtnClick(View view) {

        /*
         * here we are calling DemoActivity for example 2
         * */

        /*Checking for runtime permissions */


        requestAudioPermissions();

    }


    private void requestAudioPermissions() {
        if (ContextCompat.checkSelfPermission(clContext,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            //When permission is not granted by user, show them message why this permission is needed.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {
                Toast.makeText(clContext, "Please grant permissions to record audio", Toast.LENGTH_LONG).show();

                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_RECORD_AUDIO);

            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_RECORD_AUDIO);
            }
        }
        //If permission is granted, then go ahead recording audio
        else if (ContextCompat.checkSelfPermission(clContext,
                Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {

            //Go ahead with recording audio now

            recordAudio();
        }
    }

    //Handling callback
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 102: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    recordAudio();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(clContext, "Permissions Denied to record audio", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private void recordAudio() {

        if (!Utili.isAirplaneModeOn(clContext)) {
            if (Utili.isWifiNetWorkEnabled(clContext) && Utili.isOnline()) {
                Intent callDemoActivity = new Intent(clContext, DemoActivity.class);
                startActivity(callDemoActivity);
            } else {
                Toast.makeText(clContext, "Check your Internet connection and Try Again!..", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(clContext, "Turn off Airplane mode and Try Again!..", Toast.LENGTH_SHORT).show();
        }


    }


    public void onCmusPhinxBtnClick(View view) {

        if (!Utili.isAirplaneModeOn(clContext)) {
            if (Utili.isWifiNetWorkEnabled(clContext) && Utili.isOnline()) {
                Intent callDemoActivity = new Intent(clContext, PocketSphinxActivity.class);
                startActivity(callDemoActivity);
            } else {
                Toast.makeText(clContext, "Check your Internet connection and Try Again!..", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(clContext, "Turn off Airplane mode and Try Again!..", Toast.LENGTH_SHORT).show();
        }
    }
}
