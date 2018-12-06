package com.example.revathi.speechapp;

import java.util.HashMap;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

public class MyTextToSpeech {

    private TextToSpeech textToSpeech;

    private Activity mContext;
    private MySpeechRecognizer mSpeechRecognizer;

    private HashMap<String, String> map = new HashMap<String, String>();

    public MyTextToSpeech(Activity context, MySpeechRecognizer speechRecognizer) {
        super();

        this.mContext = context;
        this.mSpeechRecognizer = speechRecognizer;

        textToSpeech = new TextToSpeech(this.mContext, new MyTextToSpeechListener());

    }

    public void destroy() {

        textToSpeech.stop();
        textToSpeech.shutdown();

    }

    public void speakText(String p_text) {
        if (textToSpeech.isSpeaking()) {
            textToSpeech.stop();
        }
        mSpeechRecognizer.checkIsListning();
        textToSpeech.speak(p_text, TextToSpeech.QUEUE_FLUSH, map);
    }

    public void speakText(String p_text, float p_pitch, float p_rate) {

    }


    @SuppressLint("NewApi")
    class MyTextToSpeechListener implements TextToSpeech.OnInitListener {

        @Override
        public void onInit(int status) {

            if (status == TextToSpeech.SUCCESS) {
                float pitch = 1.8f;
                float rate = 0.8f;
                Locale locale = Locale.US;
                textToSpeech.setPitch(pitch);
                textToSpeech.setSpeechRate(rate);
                textToSpeech.setLanguage(locale);

                textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                    @Override
                    public void onDone(String utteranceId) {
                        Log.i(getClass().toString(), "onDone");
                        mContext.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                mSpeechRecognizer.startSpeechRecognizer();
                            }
                        });
                    }

                    @Override
                    public void onError(String utteranceId) {
                    }

                    @Override
                    public void onStart(String utteranceId) {
                    }
                });
                map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "messageID");
            }
        }
    }
}