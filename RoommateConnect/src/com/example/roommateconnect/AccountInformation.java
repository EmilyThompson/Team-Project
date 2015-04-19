package com.example.roommateconnect;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AccountInformation extends Activity implements OnClickListener,
		OnItemSelectedListener, OnCheckedChangeListener {

	// Create tag
	private static final String tag = "Account Information";

	// Create Database References
	SQLiteDatabase db;
	private static String DBName = "MATCHES.db";
	private static String Table = "MY_TABLE";
	private Cursor curse1;
	private Cursor curse2;

	// Create String for Intent Extra
	String receiving_user_accountInformation;

	// Create EditTexts
	private EditText studentID;
	private EditText firstname;
	private EditText lastname;
	private EditText phone;
	private EditText email;
	private EditText password;
	private EditText quantity;

	// Create Buttons
	private Button saveSettings;
	private Button deleteAccount;

	// Create TextViews
	private TextView classCode;
	private TextView gender;
	private TextView lookingFor;

	// Create Spinners
	private Spinner classMenu;
	private Spinner lookingForMenu;

	// Create String Arrays
	String[] classOptions = { "Sophomore", "Junior", "Senior" };
	String[] lookingForOptions = { "Room(s)", "Roommate(s)" };

	// Create Radio Buttons
	private RadioButton female;
	private RadioButton male;

	// Create Error boolean
	private boolean error = false;

	// Create Imported Data Strings
	private String STUDENTID;
	private String FIRSTNAME;
	private String LASTNAME;
	private String PHONE;
	private String EMAIL;
	private String PASSWORD;
	private String SEX;
	private String CLASSYEAR;
	private String LOOKINGFOR;
	private String QUANTITY;

	// OnCreate
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_information);

		// Link EditTexts
		studentID = (EditText) findViewById(R.id.EnterID);
		firstname = (EditText) findViewById(R.id.EnterFirstName);
		lastname = (EditText) findViewById(R.id.EnterLastName);
		phone = (EditText) findViewById(R.id.EnterPhoneNumber);
		email = (EditText) findViewById(R.id.EnterEmail);
		password = (EditText) findViewById(R.id.EnterPassword);
		quantity = (EditText) findViewById(R.id.EnterGroupAmount);

		// Link Buttons
		saveSettings = (Button) findViewById(R.id.saveSettings);
		deleteAccount = (Button) findViewById(R.id.deleteAccount);

		// Link TextViews
		classCode = (TextView) findViewById(R.id.classLevel);
		lookingFor = (TextView) findViewById(R.id.LookingFor);
		gender = (TextView) findViewById(R.id.Sex);

		// Link Spinners
		classMenu = (Spinner) findViewById(R.id.ClassSpinner);
		lookingForMenu = (Spinner) findViewById(R.id.EnterLookingFor);

		// Link Radio Buttons
		male = (RadioButton) findViewById(R.id.Male);
		female = (RadioButton) findViewById(R.id.Female);

		// Set Listeners
		saveSettings.setOnClickListener(this);
		deleteAccount.setOnClickListener(this);
		female.setOnCheckedChangeListener(this);
		male.setOnCheckedChangeListener(this);

		// Set Spinner classMenu
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

		// Get & Store Message from Intent
		Intent intent = getIntent();
		receiving_user_accountInformation = intent.getStringExtra("USER_ACCOUNT");

		// Call methods
		loadInformation(receiving_user_accountInformation);
		setInformation();
	}

	// Load Information Method
	private void loadInformation(String userString) {
		db = openOrCreateDatabase(DBName, Context.MODE_PRIVATE, null);
		try {
			Cursor cursor = db
					.rawQuery(
							"SELECT "
									+ "StudentID, FirstName, LastName, PhoneNumber, Email, Password, Sex, ClassLevel, LookingFor, GroupAmount "
									+ "FROM " + Table + " WHERE Email='"
									+ userString + "'", null);

			cursor.moveToFirst();
			STUDENTID = cursor.getString(cursor.getColumnIndex("StudentID"));
			FIRSTNAME = cursor.getString(cursor.getColumnIndex("FirstName"));
			LASTNAME = cursor.getString(cursor.getColumnIndex("LastName"));
			PHONE = cursor.getString(cursor.getColumnIndex("PhoneNumber"));
			EMAIL = cursor.getString(cursor.getColumnIndex("Email"));
			PASSWORD = cursor.getString(cursor.getColumnIndex("Password"));
			SEX = cursor.getString(cursor.getColumnIndex("Sex"));
			CLASSYEAR = cursor.getString(cursor.getColumnIndex("ClassLevel"));
			LOOKINGFOR = cursor.getString(cursor.getColumnIndex("LookingFor"));
			QUANTITY = cursor.getString(cursor.getColumnIndex("GroupAmount"));
		} catch (Exception e) {
			Log.i(tag, "Error in loading student information.");
		}
	}

	// Set Information Method
	public void setInformation() {
		studentID.setText(STUDENTID);
		firstname.setText(FIRSTNAME);
		lastname.setText(LASTNAME);
		phone.setText(PHONE);
		email.setText(EMAIL);
		password.setText(PASSWORD);
		if (SEX.equals("Female")) {
			female.setChecked(true);
			male.setChecked(false);
		} else if (SEX.equals("Male")) {
			male.setChecked(true);
			female.setChecked(false);
		}
		if (CLASSYEAR.equals("Sophomore")) {
			classMenu.setSelection(0);
		} else if (CLASSYEAR.equals("Junior")) {
			classMenu.setSelection(1);
		} else if (CLASSYEAR.equals("Senior")) {
			classMenu.setSelection(2);
		}
		if (LOOKINGFOR.equals("Room(s)")) {
			lookingForMenu.setSelection(0);
		} else if (LOOKINGFOR.equals("Roommate(s)")) {
			lookingForMenu.setSelection(1);
		}
		quantity.setText(QUANTITY);
	}

	// Radio Button Listener Method
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

	// Spinner Listener Method
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

	// Button Listener Method
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.saveSettings:
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
			if (curse1.getCount() > 0
					&& !studentID.getText().toString().equals(STUDENTID)) {
				studentID.setError("ID is already in use, please sign in");
				error = true;
				Log.i(tag, "StudentID already in use error");
			}
			if (curse2.getCount() > 0
					&& !email.getText().toString().equals(EMAIL)) {
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
				updateTable();
				Intent s = new Intent(AccountInformation.this,
						AccountHome.class);
				startActivity(s);
			}
			break;
		case R.id.deleteAccount:
			Log.i(tag, "Delete Account Button clicked");
			deleteAccount();
			Toast.makeText(getApplicationContext(), "Account has been deleted",
					Toast.LENGTH_SHORT).show();
			Intent d = new Intent(AccountInformation.this, Welcome.class);
			startActivity(d);
			break;
		}
	}

	// Delete Account Method
	private void deleteAccount() {
		String identify = "StudentID='" + STUDENTID + "'";
		db.delete(Table, identify, null);
	}

	// Update Table Method
	private void updateTable() {
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
			values.put("LookingFor", LOOKINGFOR);
			values.put("GroupAmount", quantity.getText().toString());
			String identify = "StudentID='" + STUDENTID + "'";
			db.update(Table, values, identify, null);
			Toast.makeText(getApplicationContext(),
					"Account Information Updated", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"Error in inserting into table", Toast.LENGTH_LONG).show();
		}
	}

}