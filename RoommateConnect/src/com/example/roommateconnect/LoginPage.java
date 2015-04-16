package com.example.roommateconnect;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LoginPage extends Activity implements OnClickListener {

	SQLiteDatabase db;
	LinearLayout Linear;
	private static String DBName = "MATCHES.db";
	private static String Table = "MY_TABLE";
	
	private Button login;
	private EditText username;
	private EditText password;
	
	private static final String tag = "LoginPage";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_page);

		login = (Button) findViewById(R.id.loginButtonLP);
		login.setOnClickListener(this);

		username = (EditText) findViewById(R.id.EnterUsernameLP);
		password = (EditText) findViewById(R.id.EnterPasswordLP);
	}

	@Override
	public void onClick(View v) {

		String user = username.getText().toString();
		Log.i(tag, "Username is " + user);
		String pass = password.getText().toString();
		Log.i(tag, "Password is " + pass);
		
		if(doesAccountExist(user, pass)){
			
		Intent it = new Intent();
		it.setComponent(new ComponentName("com.example.roommateconnect",
				"com.example.roommateconnect.AccountHome"));
		startActivity(it);
	}
		else {
			Toast.makeText(getApplicationContext(), "Account does not exist", Toast.LENGTH_SHORT).show();
		}
	}

	// Check Username and Password to see if account exists
	public boolean doesAccountExist(String u, String p){
		boolean exist = false;
		try{
		db.execSQL("SELECT Email, Username FROM " + Table + "WHERE Email=" + u + "AND WHERE Password=" + p);
		exist = true;
		}
		catch (Exception e){
			Log.i(tag, "Could not find account");
			exist = false;
		}
		return exist;		
	}
}
