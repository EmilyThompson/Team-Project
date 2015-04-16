package com.example.roommateconnect;

import android.app.Activity; 
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class Welcome extends Activity implements OnClickListener {
	
	SQLiteDatabase db;
	LinearLayout Linear;
	private static String DBName = "MATCHES.db";
	private static String Table = "MY_TABLE";
	private Button LoginButton;
	private Button CreateAccountButton;
	private static final String tag = "Welcome page";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		Linear = (LinearLayout) findViewById(R.id.linearLayout);
		
		LoginButton = (Button) findViewById(R.id.LoginButton);
		LoginButton.setOnClickListener(this);

		CreateAccountButton = (Button) findViewById(R.id.CreateAccountButton);
		CreateAccountButton.setOnClickListener(this);

	}

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
	
	// Create Table if it doesn't exist
	public void createTable(){
		 try{
		        db = openOrCreateDatabase(DBName, Context.MODE_PRIVATE,null);			    			        
		        db.execSQL("CREATE TABLE IF  NOT EXISTS "+ Table +" (StudentID TEXT PRIMARY KEY, FirstName TEXT, LastName TEXT, "
		       		+ "PhoneNumber TEXT, Email TEXT, Password TEXT, Sex TEXT, ClassLevel TEXT, LookingFor TEXT, GroupAmount INTEGER );");
		       Toast.makeText(getApplicationContext(), "MY_TABLE has been created", Toast.LENGTH_LONG).show();
		        db.close();
		        }catch(Exception e){
		            Toast.makeText(getApplicationContext(), "Error in creating table", Toast.LENGTH_LONG).show();	
	    }
	}
}
