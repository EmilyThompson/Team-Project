package com.example.roommateconnect;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.database.sqlite.SQLiteDatabase;

public class Welcome extends Activity implements OnClickListener,
		OnInitListener {

	// Create Tag
	private static final String tag = "Welcome page";

	// Create Database references
	SQLiteDatabase db;
	private static String DBName = "MATCHES.db";
	private static String Table = "MY_TABLE";

	// Create Buttons
	private Button LoginButton;
	private Button CreateAccountButton;

	// Text to speech
	private TextToSpeech speaker;

	// Image and Animation
	private ImageView image;

	// OnCreate
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		// Link Buttons
		LoginButton = (Button) findViewById(R.id.LoginButton);
		CreateAccountButton = (Button) findViewById(R.id.CreateAccountButton);

		// Create Button Listeners
		LoginButton.setOnClickListener(this);
		CreateAccountButton.setOnClickListener(this);

		// Link Image and Create Animation
		image = (ImageView) findViewById(R.id.image);
		image.setImageResource(R.drawable.image1);
		createTable();

		Animation an = AnimationUtils.loadAnimation(this,
				R.anim.simple_animation);
		an.setAnimationListener(new MyAnimationListener());
		image.startAnimation(an);

		speaker = new TextToSpeech(this, this);

	}

	class MyAnimationListener implements Animation.AnimationListener {

		@Override
		public void onAnimationEnd(Animation animation) {
			// do nothing
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// do nothing
		}

		@Override
		public void onAnimationStart(Animation animation) {
			// do nothing

		}

	}

	public void speak(String output) {
		speaker.speak(output, TextToSpeech.QUEUE_FLUSH, null);
	}

	// Implements TextToSpeech.OnInitListener.
	@Override
	public void onInit(int status) {
		// status can be either TextToSpeech.SUCCESS or TextToSpeech.ERROR.
		if (status == TextToSpeech.SUCCESS) {
			// Set preferred language to US english.
			// If a language is not be available, the result will indicate it.
			int result = speaker.setLanguage(Locale.US);

			// int result = speaker.setLanguage(Locale.FRANCE);
			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				// Language data is missing or the language is not supported.
				Log.e(tag, "Language is not available.");
			} else {
				// The TTS engine has been successfully initialized
				speak("Welcome!");
				Log.i(tag, "TTS Initialization successful.");
				Log.i(tag, "Speech: Welcome.");
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
		case R.id.LoginButton:
			Log.i(tag, "Login Button Clicked");
			Intent wL = new Intent(Welcome.this, LoginPage.class);
			startActivity(wL);
			break;
		case R.id.CreateAccountButton:
			Log.i(tag, "Create acount Button Clicked");
			Intent wA = new Intent(Welcome.this, CreateAccount.class);
			startActivity(wA);
			break;
		}
	}

	// Create Table Method
	public void createTable() {
		try {
			db = openOrCreateDatabase(DBName, Context.MODE_PRIVATE, null);
			db.execSQL("CREATE TABLE IF  NOT EXISTS "
					+ Table
					+ " (StudentID TEXT PRIMARY KEY, FirstName TEXT, LastName TEXT, "
					+ "PhoneNumber TEXT, Email TEXT, Password TEXT, Sex TEXT, ClassLevel TEXT, LookingFor TEXT, GroupAmount TEXT );");
			Log.i(tag, "MY_TABLE is/has been created");

			db.close();
		} catch (Exception e) {
			Log.i(tag, "Error in creating table");
		}
	}
}
