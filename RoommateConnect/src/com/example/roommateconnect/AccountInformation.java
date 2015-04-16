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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AccountInformation extends Activity implements OnClickListener,
		OnItemSelectedListener, OnCheckedChangeListener {

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
		quantity = (EditText) findViewById(R.id.EnterGroupAmount);
		RadioButton f = (RadioButton) findViewById(R.id.Female);
		f.setOnCheckedChangeListener(this);
		RadioButton m = (RadioButton) findViewById(R.id.Male);
		m.setOnCheckedChangeListener(this);
		

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

	//	db = openOrCreateDatabase(DBName, Context.MODE_PRIVATE, null);
	//	db.execSQL("DROP TABLE IF EXISTS MY_TABLE");
	//	Toast.makeText(getApplicationContext(), "Table dropped", Toast.LENGTH_SHORT).show();
		}
	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	switch (buttonView.getId()){
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
		/*	FIRSTNAME = firstname.getText().toString();
			LASTNAME = lastname.getText().toString();
			EMAIL = email.getText().toString();
			PASSWORD = password.getText().toString();
			STRING_QUANTITY = quantity.getText().toString();
			QUANTITY = Integer.parseInt(STRING_QUANTITY);*/
			insertIntoTable();
			Toast.makeText(getApplicationContext(), "Data added to database", Toast.LENGTH_SHORT).show();
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
	public void deleteTable() {
		try {
			db = openOrCreateDatabase(DBName, Context.MODE_PRIVATE, null);
			db.execSQL("DROP MY_TABLE");
			Toast.makeText(getApplicationContext(), Table + "has been deleted", Toast.LENGTH_LONG).show();
			db.close();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Error in creating table",
					Toast.LENGTH_LONG);
		}
	}
	
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
			values.put("First Name", firstname.getText().toString());
			values.put("Last Name", lastname.getText().toString());
			values.put("Email", email.getText().toString());
			values.put("Password", password.getText().toString());
			if (SEX.equals("Female")){
			values.put("Sex", "Female");
		}
			else if (SEX.equals("Male")){
				values.put("Sex", "Male");
			}
			values.put("Class Level", classCode.getText().toString());
			values.put("Looking for", lookingFor.getText().toString());
			values.put("Group Amount", quantity.getText().toString());
			db.insert("Account", null, values);
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"Error in inserting into table", Toast.LENGTH_LONG);
		}
	}
}
