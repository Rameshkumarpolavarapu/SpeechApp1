package com.example.revathi.speechapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomRecog extends AppCompatActivity  implements VoiceRecognizerInterface {
    Button button ;
    TextView textView;
    String string;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_custom_recog);
            textView = (TextView) findViewById(R.id.textView);
        }



    public void cusOnclick(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null && fragmentManager.findFragmentByTag("dialogVoiceRecognizer") == null && !isFinishing()) {
            VoiceRecognizerDialogFragment languageDialogFragment = new VoiceRecognizerDialogFragment(this,this);
            languageDialogFragment.show(fragmentManager, "dialogVoiceRecognizer");
        }
    }

    @Override
    public void spokenText(String spokenText) {
        textView.setText(spokenText);
    }

}


