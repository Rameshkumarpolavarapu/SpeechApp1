package com.example.revathi.speechapp;

import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Toast;

public class MySpeechRecognizer {
	
	private Context mContext;
	
	private SpeechRecognizer speechRecognizer;
	private Intent recognizerIntent;
	
	private boolean isListening = false;


	public MySpeechRecognizer(Context context) {
		this.mContext = context;
				
		createSpeechRecognizer();
		initIntent();
	}
	
	private void createSpeechRecognizer() {
		if (speechRecognizer != null) {
			speechRecognizer.cancel();
			speechRecognizer.destroy();
		}
		speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this.mContext);
		speechRecognizer.setRecognitionListener(new MyRecognitionListener());
		
	}

	private void initIntent() {
		
		recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, mContext.getPackageName());
		setLocale(Locale.US);

	}
	
	public void setLocale(Locale locale) {
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, locale.toString());
	}
	
	
	public void startSpeechRecognizer() {
		
		if (!SpeechRecognizer.isRecognitionAvailable(this.mContext)) {
			createSpeechRecognizer();
		}
		
		speechRecognizer.startListening(recognizerIntent);
		isListening = true;
		
	}
	
	public void checkIsListning() {
		if (isListening) {
			speechRecognizer.cancel();
		}
	}
	

	
	public void destroy() {
	
		speechRecognizer.cancel();
		speechRecognizer.destroy();
		isListening = false;
		
	}
	
	class MyRecognitionListener implements RecognitionListener {
		
		public MyRecognitionListener() {
			super();
			
		}

		@Override
		public void onBeginningOfSpeech() {
			Log.d(getClass().toString(), "onBeginningOfSpeech");
		}

		@Override
		public void onBufferReceived(byte[] buffer) {
			
		}

		@Override
		public void onEndOfSpeech() {
			Log.d(getClass().toString(), "onEndOfSpeech");

		}

		@Override
		public void onError(int error) {
			Log.d(getClass().toString(), "onError:"+String.valueOf(error));
			switch (error) {
		    case SpeechRecognizer.ERROR_AUDIO:
		        // Audio data storage failure
		        break;
		    case SpeechRecognizer.ERROR_CLIENT:
		        // Error in Android device (other)
		        break;
		    case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
		        // No authority
		        break;
		    case SpeechRecognizer.ERROR_NETWORK:
		        // Network error (other)
		        break;
		    case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
		        // Network timeout error
		        break;
		    case SpeechRecognizer.ERROR_NO_MATCH:
		        // No speech recognition result
		        break;
		    case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
		    	destroy();
		    	createSpeechRecognizer();
		        // Can not request to RecognitionService
		        break;
		    case SpeechRecognizer.ERROR_SERVER:
		        // Error notification from Server side
		        break;
		    case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
		        // No voice input
		        break;
		    default:
		    }
			isListening = false;
			startSpeechRecognizer();
		}

		@Override
		public void onEvent(int eventType, Bundle params) {
			
		}

		@Override
		public void onPartialResults(Bundle partialResults) {
			
		}

		@Override
		public void onReadyForSpeech(Bundle params) {
			Log.d(getClass().toString(), "onReadyForSpeech");
			
		}

		@Override
		public void onResults(Bundle results) {
			
			ArrayList<String> recData = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

			String resultData   =   recData.get(0).trim();
			Log.d(getClass().toString(), resultData);
			Toast.makeText(mContext, ""+resultData, Toast.LENGTH_SHORT).show();

			m

			isListening = false;
			//startSpeechRecognizer();
		}

		@Override
		public void onRmsChanged(float rmsdB) {
			
		}
	}


}