package com.example.roommateconnect;

import java.util.Locale;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AccountHome extends Activity implements OnClickListener, OnInitListener {

	// Create Tag
	private static final String tag = "Account Home";

	// Create Database References
	private Button accountSettings;
	private Button myMatches;

	// Create String for Intent Extra
	String accountInformation;
	String sending_user_matches;
	
	//Text to Speech
	private TextToSpeech speaker;

	
	// onCreate
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_home);

		accountSettings = (Button) findViewById(R.id.accountSettings);
		accountSettings.setOnClickListener(this);
		myMatches = (Button) findViewById(R.id.myMatches);
		myMatches.setOnClickListener(this);

		Intent intent = getIntent();
		accountInformation = intent.getStringExtra("USER1");
		Log.i(tag, "Email received by Account Home equals: " + accountInformation);
		sending_user_matches = accountInformation;
		
        speaker = new TextToSpeech(this, this);

	}
	
	  public void speak(String output){
	    	speaker.speak(output, TextToSpeech.QUEUE_FLUSH, null);
	    }
	    
	    // Implements TextToSpeech.OnInitListener.
	    public void onInit(int status) {
	        // status can be either TextToSpeech.SUCCESS or TextToSpeech.ERROR.
	        if (status == TextToSpeech.SUCCESS) {
	            // Set preferred language to US english.
	            // If a language is not be available, the result will indicate it.
	            int result = speaker.setLanguage(Locale.US);
	           
	           //  int result = speaker.setLanguage(Locale.FRANCE);
	            if (result == TextToSpeech.LANG_MISSING_DATA ||
	                result == TextToSpeech.LANG_NOT_SUPPORTED) {
	               // Language data is missing or the language is not supported.
	                Log.e(tag, "Language is not available.");
	            } else {
	                  // The TTS engine has been successfully initialized
	            	//THIS IS WHERE THINGS WILL BE SAID ON THE PAGE
	            	speak("Welcome");
	            	Log.i(tag, "TTS Initialization successful.");
	            	Log.i(tag, "Things that will be said goes here");

	            }
	        } else {
	            // Initialization failed.
	            Log.e(tag, "Could not initialize TextToSpeech.");
	        }
	    }

	// Button Listener
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.accountSettings:
			Log.i(tag, "Account Settings button clicked");
			Intent kk = new Intent(AccountHome.this, AccountInformation.class);
			kk.putExtra("USER_ACCOUNT", accountInformation);
			startActivity(kk);
			break;
		case R.id.myMatches:
			Log.i(tag, "My Matches button clicked");
			Intent bb = new Intent(AccountHome.this, Matches.class);
			bb.putExtra("USER_MATCHES", sending_user_matches);
			startActivity(bb);
			Log.i(tag, "My Matches Pressed!");
			break;
		}
	}

	
}
