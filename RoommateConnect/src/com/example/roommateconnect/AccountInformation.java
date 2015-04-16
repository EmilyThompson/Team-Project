package com.example.roommateconnect;

import java.io.*; 

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AccountInformation extends Activity implements OnClickListener, OnItemSelectedListener {
	
	private static final String tag = "Account Information";
	private Button saveSettings;
	private Button deleteAccount;
	private EditText firstname;
	private EditText lastname;
	private EditText email;
	private EditText password;
	private Spinner classMenu;
	private TextView classCode;
	String [] classOptions = {"Sophomore", "Junior", "Senior"};
	private Spinner lookingForMenu;
	private TextView lookingFor;
	String [] lookingForOptions = {"Room(s)", "Roommate(s)"};
	
	//	sex
	//birthdate
	//lookingfor

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
	lookingFor = (TextView)findViewById(R.id.LookingFor);
	lookingForMenu = (Spinner)findViewById(R.id.EnterLookingFor);
	
	registerForContextMenu(classMenu);
	classMenu.setOnItemSelectedListener(this);
	
	ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, classOptions);
	aa.setDropDownViewResource(android.R.layout.simple_spinner_item);
	classMenu.setAdapter(aa);
	
	registerForContextMenu(lookingForMenu);
	lookingForMenu.setOnItemSelectedListener(this);
	
	ArrayAdapter<String> mm = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lookingForOptions);
	mm.setDropDownViewResource(android.R.layout.simple_spinner_item);
	lookingForMenu.setAdapter(mm);
	
	}
	
	
	@Override
	public void onClick(View v){
	switch (v.getId()){
	case R.id.saveSettings:
		Toast.makeText(getApplicationContext(), "Account settings saved!", Toast.LENGTH_SHORT).show();
		String filename = "database.txt";
		String fn = firstname.getText().toString();
		String ln = lastname.getText().toString();
		String eml = email.getText().toString();
		String pswd = password.getText().toString();
		Intent s = new Intent(AccountInformation.this, AccountHome.class);
		startActivity(s);
	break;
	case R.id.deleteAccount:
		Toast.makeText(getApplicationContext(), "Account has been deleted", Toast.LENGTH_SHORT).show();
		Intent d = new Intent(AccountInformation.this, Welcome.class);
		startActivity(d);
	break;
	}
		
		}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		Spinner spin = (Spinner) parent;
		if (spin.getId()==R.id.ClassSpinner){
		String code = (classOptions[position]);
		}
		else if (spin.getId()==R.id.EnterLookingFor){
		String look = (lookingForOptions[position]);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		classCode.setText("Please Make Selection");
	}

}
	
	

	/*try {
	File F = new File(getFilesDir(), filename);
	if (F.exists()) 
	{}
	else {F.createNewFile();}
	SQLiteDatabase db = openOrCreateDatabase("Accounts.db", Context.MODE_PRIVATE, null);
	
	db.findEditTable("AccountInfo");
	db.execSQL("CREATE TABLE AccountInfo");
	
	ContentValues values = new ContentValues();
	values.put("First Name", fn);
	values.put("Last Name", ln);
	values.put("College", col);
	values.put("City", cit);
	/,values.put("State", st);
	values.put("Zip Code", zip);
	values.put("Within Distance", mi);
	} catch (IOException e){
		Log.e(tag,"IO Exception thrown.");
	}*/

