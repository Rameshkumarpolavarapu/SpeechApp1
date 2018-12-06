package com.example.revathi.speechapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SingleButton extends AppCompatActivity {

    private MyTextToSpeech mTextToSpeech = null;
    private MySpeechRecognizer mSpeechRecognizer = null;
    private Context clContext;
    public final int RESULT_SPEECH = 101;
    private final int MY_PERMISSIONS_RECORD_AUDIO = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clContext = this;
        this.mTextToSpeech = new MyTextToSpeech(this, mSpeechRecognizer);
        this.mSpeechRecognizer = new MySpeechRecognizer(this);
        setContentView(R.layout.activity_single_button);

        Button button = (Button) findViewById(R.id.btn1);
        assert button != null;
        button.setOnClickListener(new CLMyOnClickListener());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSpeechRecognizer != null)
            mSpeechRecognizer.destroy();
        if (mTextToSpeech != null)
            mTextToSpeech.destroy();
    }

    private class CLMyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int iId = v.getId();
            switch (iId) {
                case R.id.btn1:
                    requestAudioPermissions();
                    break;
            }
        }
    }

    public void startSpeechRecognizer() {
        /*if (mSpeechRecognizer != null){
            mSpeechRecognizer.checkIsListning();
            mSpeechRecognizer.startSpeechRecognizer();
        }*/

        mTextToSpeech.speakText("Hello");
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

            startSpeechRecognizer();
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
                    startSpeechRecognizer();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(clContext, "Permissions Denied to record audio", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}
