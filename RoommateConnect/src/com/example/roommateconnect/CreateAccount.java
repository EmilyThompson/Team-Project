package com.example.roommateconnect;

import java.util.Locale;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CreateAccount extends Activity implements OnClickListener,
		OnItemSelectedListener, OnCheckedChangeListener, OnInitListener {

	// Create Tag
	private static final String tag = "CreateAccount";

	// Create Database References
	SQLiteDatabase db;
	private static String DBName = "MATCHES.db";
	private static String Table = "MY_TABLE";
	private Cursor curse1;
	private Cursor curse2;

	// Create Buttons
	private Button createAccount;

	// Create EditTexts
	private EditText studentID;
	private EditText firstname;
	private EditText lastname;
	private EditText phone;
	private EditText email;
	private EditText password;
	private EditText quantity;

	// Create TextViews
	private TextView lookingFor;
	private TextView classCode;
	private TextView gender;

	// Create Spinners
	private Spinner classMenu;
	private Spinner lookingForMenu;

	// Create String Arrays
	String[] classOptions = { "Sophomore", "Junior", "Senior" };
	String[] lookingForOptions = { "Room(s)", "Roommate(s)" };

	// Create EditTexts
	private RadioButton male;
	private RadioButton female;

	// Create Boolean for Errors
	private boolean error = false;

	// Create Strings
	private String CLASSYEAR;
	private String LOOKINGFOR;
	private String SEX;

	private TextToSpeech speaker;

	// onCreate
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);

		// Link Button
		createAccount = (Button) findViewById(R.id.createAccount);

		// Link EditTexts
		studentID = (EditText) findViewById(R.id.EnterID);
		firstname = (EditText) findViewById(R.id.EnterFirstName);
		lastname = (EditText) findViewById(R.id.EnterLastName);
		phone = (EditText) findViewById(R.id.EnterPhoneNumber);
		email = (EditText) findViewById(R.id.EnterEmail);
		password = (EditText) findViewById(R.id.EnterPassword);
		quantity = (EditText) findViewById(R.id.EnterGroupAmount);

		// Link TextViews
		classCode = (TextView) findViewById(R.id.classLevel);
		lookingFor = (TextView) findViewById(R.id.LookingFor);
		gender = (TextView) findViewById(R.id.Sex);

		// Link Spinners
		classMenu = (Spinner) findViewById(R.id.ClassSpinner);
		lookingForMenu = (Spinner) findViewById(R.id.EnterLookingFor);

		// Link RadioButton
		female = (RadioButton) findViewById(R.id.Female);
		male = (RadioButton) findViewById(R.id.Male);

		// Set Listeners
		createAccount.setOnClickListener(this);
		female.setOnCheckedChangeListener(this);
		male.setOnCheckedChangeListener(this);

		// Set Spinner clasMenu
		registerForContextMenu(classMenu);
		classMenu.setOnItemSelectedListener(this);
		ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, classOptions);
		aa.setDropDownViewResource(android.R.layout.simple_spinner_item);
		classMenu.setAdapter(aa);

		// Set Spinner lookingForMenu
		registerForContextMenu(lookingForMenu);
		lookingForMenu.setOnItemSelectedListener(this);
		ArrayAdapter<String> mm = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, lookingForOptions);
		mm.setDropDownViewResource(android.R.layout.simple_spinner_item);
		lookingForMenu.setAdapter(mm);

		// Initialize Text to Speech engine (context, listener object)
		speaker = new TextToSpeech(this, this);

	}

	// speaks the contents of output
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
				speak("Please enter account info");
				Log.i(tag, "TTS Initialization successful.");
			}
		} else {
			// Initialization failed.
			Log.e(tag, "Could not initialize TextToSpeech.");
		}
	}

	// on destroy
	@Override
	public void onDestroy() {

		// shut down TTS engine
		if (speaker != null) {
			speaker.stop();
			speaker.shutdown();
		}
		super.onDestroy();
	}

	// Radio Button Listener
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.Female:
			SEX = "Female";
			break;
		case R.id.Male:
			SEX = "Male";
			break;
		}
	}

	// Spinner Listener
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		Spinner spin = (Spinner) parent;
		if (spin.getId() == R.id.ClassSpinner) {
			CLASSYEAR = (classOptions[position]);
		} else if (spin.getId() == R.id.EnterLookingFor) {
			LOOKINGFOR = (lookingForOptions[position]);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		classCode.setText("Please Make Selection");
	}

	// Button Listener
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.createAccount:
			error = false;
			studentID.setError(null);
			firstname.setError(null);
			lastname.setError(null);
			phone.setError(null);
			email.setError(null);
			password.setError(null);
			gender.setError(null);
			classCode.setError(null);
			lookingFor.setError(null);
			quantity.setError(null);

			Log.i(tag,
					"CreateAccount button clicked... All errors set to null...Error status:"
							+ error);
			db = openOrCreateDatabase(DBName, Context.MODE_PRIVATE, null);
			curse1 = db.rawQuery("SELECT StudentID FROM " + Table
					+ " WHERE StudentID='" + studentID.getText().toString()
					+ "'", null);
			curse2 = db
					.rawQuery("SELECT Email FROM " + Table + " WHERE Email='"
							+ email.getText().toString() + "'", null);
			if (curse1.getCount() > 0) {
				studentID.setError("ID is already in use, please sign in");
				error = true;
				Log.i(tag, "StudentID already in use error");
			}
			if (curse2.getCount() > 0) {
				email.setError("Email is already in use, please sign in");
				error = true;
				Log.i(tag, "Email in use error");
			}
			if (studentID.getText().toString().length() != 8) {
				studentID.setError("Invalid Student ID");
				error = true;
				Log.i(tag, "Invalid StudentID error");
			}
			if (firstname.getText().toString().equals("")) {
				firstname.setError("Field cannot be left blank.");
				error = true;
				Log.i(tag, "First name is null error");
			}
			if (lastname.getText().toString().equals("")) {
				lastname.setError("Field cannot be left blank.");
				error = true;
				Log.i(tag, "Lastname is null error");
			}
			if (phone.getText().toString().length() != 10) {
				phone.setError("Invalid Phone. Enter 10 digit number no dashes");
				error = true;
				Log.i(tag, "Phone number error");
			}
			if (email.getText().length() == 0) {
				email.setError("Field cannot be left blank.");
				error = true;
				Log.i(tag, "Email null error");
			}
			if (email.getText().toString().indexOf("@") == -1) {
				email.setError("Invalid Email");
				error = true;
				Log.i(tag, "Invalid email, no @ sign error");
			}
			if (password.getText().length() < 6) {
				password.setError("Password must be more than 6 characters");
				error = true;
				Log.i(tag, "Password error");
			}
			if (!female.isChecked() && !male.isChecked()) {
				gender.setError("Please select gender");
				error = true;
				Log.i(tag, "Gender error");
			} else if (error == false) {
				Log.i(tag, "No Errors!");
				Toast.makeText(getApplicationContext(),
						"Account has been created!", Toast.LENGTH_SHORT).show();
				insertIntoTable();
				Intent s = new Intent(CreateAccount.this, AccountHome.class);
				s.putExtra("USER1", email.getText().toString());
				startActivity(s);
			}
			break;
		}

	}

	// insertIntoTable Method
	public void insertIntoTable() {
		Log.i(tag, "insertIntoTable method called sucessfully");
		try {
			db = openOrCreateDatabase(DBName, Context.MODE_PRIVATE, null);
			ContentValues values = new ContentValues();
			values.put("StudentID", studentID.getText().toString());
			values.put("FirstName", firstname.getText().toString());
			values.put("LastName", lastname.getText().toString());
			values.put("PhoneNumber", phone.getText().toString());
			values.put("Email", email.getText().toString());
			values.put("Password", password.getText().toString());
			if (SEX.equals("Female")) {
				values.put("Sex", "Female");
			} else if (SEX.equals("Male")) {
				values.put("Sex", "Male");
			}
			values.put("ClassLevel", CLASSYEAR);
			values.put("Lookingfor", LOOKINGFOR);
			values.put("GroupAmount", quantity.getText().toString());
			db.insert(Table, null, values);
			Log.i(tag, "Data added to database");
		} catch (Exception e) {
			Log.i(tag, "Error inserting table");
			Toast.makeText(getApplicationContext(),
					"Error in inserting into table", Toast.LENGTH_LONG).show();
		}
	}
}
