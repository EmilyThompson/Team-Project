package com.example.roommateconnect;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AccountInformation extends Activity implements OnClickListener,
		OnItemSelectedListener {

	// Create Database References
	SQLiteDatabase db;
	LinearLayout Linear;
	private static String DBName = "MATCHES.db";
	private static String Table = "MY_TABLE";

	// Create References
	private static final String tag = "Account Information";
	private Button saveSettings;
	private Button deleteAccount;
	private EditText firstname;
	private EditText lastname;
	private EditText email;
	private EditText password;
	private Spinner classMenu;
	private TextView classCode;
	String[] classOptions = { "Sophomore", "Junior", "Senior" };
	private Spinner lookingForMenu;
	private TextView lookingFor;
	String[] lookingForOptions = { "Room(s)", "Roommate(s)" };
	private EditText quantity;

	// Create Data
	private String FIRSTNAME;
	private String LASTNAME;
	private String EMAIL;
	private String PASSWORD;
	private String CLASSYEAR;
	private String LOOKINGFOR;
	private String STRING_QUANTITY;
	private int QUANTITY;
	private String SEX;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_information);

		saveSettings = (Button) findViewById(R.id.saveSettings);
		saveSettings.setOnClickListener(this);
		deleteAccount = (Button) findViewById(R.id.deleteAccount);
		deleteAccount.setOnClickListener(this);

		firstname = (EditText) findViewById(R.id.EnterFirstName);
		lastname = (EditText) findViewById(R.id.EnterLastName);
		email = (EditText) findViewById(R.id.EnterEmail);
		password = (EditText) findViewById(R.id.EnterPassword);
		classMenu = (Spinner) findViewById(R.id.ClassSpinner);
		classCode = (TextView) findViewById(R.id.classLevel);
		lookingFor = (TextView) findViewById(R.id.LookingFor);
		lookingForMenu = (Spinner) findViewById(R.id.EnterLookingFor);
		quantity = (EditText) findViewById(R.id.GroupAmount);
		RadioButton f = (RadioButton) findViewById(R.id.Female);
		RadioButton m = (RadioButton) findViewById(R.id.Male);

		registerForContextMenu(classMenu);
		classMenu.setOnItemSelectedListener(this);

		ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, classOptions);
		aa.setDropDownViewResource(android.R.layout.simple_spinner_item);
		classMenu.setAdapter(aa);

		registerForContextMenu(lookingForMenu);
		lookingForMenu.setOnItemSelectedListener(this);

		ArrayAdapter<String> mm = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, lookingForOptions);
		mm.setDropDownViewResource(android.R.layout.simple_spinner_item);
		lookingForMenu.setAdapter(mm);

		createTable();

	}

	public void onCheckedChangedListener(RadioGroup group, int checkedId) {
		switch (group.getId()) {
		case R.id.Female:
			SEX = "Female";
			break;
		case R.id.Male:
			SEX = "Male";
			break;
		}
	}

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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.saveSettings:
			Toast.makeText(getApplicationContext(), "Account settings saved!",
					Toast.LENGTH_SHORT).show();
			FIRSTNAME = firstname.getText().toString();
			LASTNAME = lastname.getText().toString();
			EMAIL = email.getText().toString();
			PASSWORD = password.getText().toString();
			STRING_QUANTITY = quantity.getText().toString();
			QUANTITY = Integer.parseInt(STRING_QUANTITY);
			insertIntoTable();
			Intent s = new Intent(AccountInformation.this, AccountHome.class);
			startActivity(s);
			break;
		case R.id.deleteAccount:
			Toast.makeText(getApplicationContext(), "Account has been deleted",
					Toast.LENGTH_SHORT).show();
			Intent d = new Intent(AccountInformation.this, Welcome.class);
			startActivity(d);
			break;
		}

	}

	// Create Table if it doesn't exist
	public void createTable() {
		try {
			db = openOrCreateDatabase(DBName, Context.MODE_PRIVATE, null);
			db.execSQL("CREATE TABLE IF  NOT EXISTS " + Table
					+ " (ID INTEGER PRIMARY KEY, NAME TEXT, PLACE TEXT);");
			db.close();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Error in creating table",
					Toast.LENGTH_LONG);
		}
	}

	// Insert Data into Database
	public void insertIntoTable() {
		try {
			db = openOrCreateDatabase(DBName, Context.MODE_PRIVATE, null);
			ContentValues values = new ContentValues();
			values.put("First Name", FIRSTNAME);
			values.put("Last Name", LASTNAME);
			values.put("Email", EMAIL);
			values.put("Password", PASSWORD);
			values.put("Sex", SEX);
			values.put("Class Level", CLASSYEAR);
			values.put("Looking for", LOOKINGFOR);
			values.put("Group Amount", QUANTITY);
			db.insert("Account", null, values);
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"Error in inserting into table", Toast.LENGTH_LONG);
		}
	}
}