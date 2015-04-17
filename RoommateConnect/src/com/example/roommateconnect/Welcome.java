package com.example.roommateconnect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;

public class Welcome extends Activity implements OnClickListener {

	// Create Tag
	private static final String tag = "Welcome page";

	// Create Database references
	SQLiteDatabase db;
	private static String DBName = "MATCHES.db";
	private static String Table = "MY_TABLE";

	// Create Buttons
	private Button LoginButton;
	private Button CreateAccountButton;

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

		createTable();
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
