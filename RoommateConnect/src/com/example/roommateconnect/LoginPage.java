package com.example.roommateconnect;

import android.app.Activity;
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
import android.widget.Toast;

public class LoginPage extends Activity implements OnClickListener {

	// Create Tag
	private static final String tag = "LoginPage";

	// Create Database References
	SQLiteDatabase db;
	private static String DBName = "MATCHES.db";
	private static String Table = "MY_TABLE";

	// Create Button
	private Button login;

	// Create EditTexts
	private EditText username;
	private EditText password;

	// Create Strings
	String user;
	String pass;

	// onCreate
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_page);

		// Link EditTexts
		username = (EditText) findViewById(R.id.EnterUsernameLP);
		password = (EditText) findViewById(R.id.EnterPasswordLP);

		// Link Button
		login = (Button) findViewById(R.id.loginButtonLP);

		// Set Button Listener
		login.setOnClickListener(this);
	}

	// Button Listener
	@Override
	public void onClick(View v) {
		Log.i(tag, "Login Button Clicked");
		db = openOrCreateDatabase(DBName, Context.MODE_PRIVATE, null);

		user = username.getText().toString();
		Log.i(tag, "Username is: " + user);

		pass = password.getText().toString();
		Log.i(tag, "Password is: " + pass);

		String sending_user_1 = user;

		if (doesAccountExist(user, pass)) {
			Log.i(tag, "doesAccountExist(user,pass) is true");

			Intent it = new Intent(LoginPage.this, AccountHome.class);
			it.putExtra("USER1", sending_user_1);
			Log.i(tag, "Email sent to Account Home equals " + sending_user_1);
			startActivity(it);
		} else {
			Log.i(tag, "doesAccountExist(user,pass) is false");
			Toast.makeText(getApplicationContext(),
					"Username or Password is incorrect", Toast.LENGTH_SHORT)
					.show();
		}

	}

	// doesAccountExist Method
	public boolean doesAccountExist(String u, String p) {
		boolean exist = false;
		try {
			Cursor cursor = db
					.rawQuery("SELECT Email, Password FROM " + Table
							+ " WHERE Email='" + u + "' AND Password='" + p
							+ "'", null);
			Log.i(tag, "Account exists");

			if (cursor.getCount() == 1) {
				exist = true;
			}
		} catch (Exception e) {
			Log.i(tag, "Could not find account");
			exist = false;
		}
		return exist;
	}
}
