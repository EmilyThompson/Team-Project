package com.example.roommateconnect;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Identity;
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
	private EditText studentID;
	private EditText firstname;
	private EditText lastname;
	private EditText phone;
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
	private String STUDENTID;
	private String FIRSTNAME;
	private String LASTNAME;
	private String PHONE;
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

		studentID = (EditText)findViewById(R.id.EnterID);
		firstname = (EditText) findViewById(R.id.EnterFirstName);
		lastname = (EditText) findViewById(R.id.EnterLastName);
		phone = (EditText)findViewById(R.id.EnterPhoneNumber);
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
			
			if (lastname.getText().length()==0)			
			lastname.setError("Field cannot be left blank.");
			else if (firstname.getText().length()==0)			
				firstname.setError("Field cannot be left blank.");
			else if (email.getText().length()==0)			
				email.setError("Field cannot be left blank.");
			else if (password.getText().length()==0)			
				password.setError("Field cannot be left blank.");
			
			Toast.makeText(getApplicationContext(), "Account settings saved!",
					Toast.LENGTH_SHORT).show();
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
	// Get values from Database
	public void createTable() {
		try {
			db = openOrCreateDatabase(DBName, Context.MODE_PRIVATE, null);
			db.execSQL("CREATE TABLE IF  NOT EXISTS " + Table
					+ " (ID INTEGER PRIMARY KEY, NAME TEXT, PLACE TEXT);");
			db.close();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Error in creating table", Toast.LENGTH_SHORT).show();
		}
	}
	
	// Insert Data into Database
	public void insertIntoTable() {
		try {
			db = openOrCreateDatabase(DBName, Context.MODE_PRIVATE, null);
			ContentValues values = new ContentValues();
			values.put("StudentID", studentID.getText().toString());
			values.put("FirstName", firstname.getText().toString());
			values.put("LastName", lastname.getText().toString());
			values.put("PhoneNumber", phone.getText().toString());
			values.put("Email", email.getText().toString());
			values.put("Password", password.getText().toString());
			if (SEX.equals("Female")){
			values.put("Sex", "Female");
		}
			else if (SEX.equals("Male")){
				values.put("Sex", "Male");
			}
			values.put("ClassLevel", CLASSYEAR);
			values.put("Lookingfor", LOOKINGFOR);
			values.put("GroupAmount", quantity.getText().toString());
		//	db.update(Table, null, values);
		//	Toast.makeText(getApplicationContext(), "Data added to database", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"Error in inserting into table", Toast.LENGTH_LONG).show();
		}
	}
}
