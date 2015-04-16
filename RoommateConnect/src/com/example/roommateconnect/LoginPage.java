package com.example.roommateconnect;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LoginPage extends Activity implements OnClickListener {

	SQLiteDatabase mydb;
	LinearLayout Linear;
	private static String DBName = "MATCHES.db";
	private static String Table = "MY_TABLE";
	
	private Button login;
	private EditText username;
	private EditText password;

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
		String pass = password.getText().toString();

		Intent it = new Intent();
		it.setComponent(new ComponentName("com.example.roommateconnect",
				"com.example.roommateconnect.AccountHome"));
		startActivity(it);
	}
	// Create Table if it doesn't exist
		public void createTable(){
			 try{
			        mydb = openOrCreateDatabase(DBName, Context.MODE_PRIVATE,null);
			        mydb.execSQL("CREATE TABLE IF  NOT EXISTS "+ Table +" (ID INTEGER PRIMARY KEY, NAME TEXT, PLACE TEXT);");
			        mydb.close();
			        }catch(Exception e){
			            Toast.makeText(getApplicationContext(), "Error in creating table", Toast.LENGTH_LONG);	
		    }
		}
}
