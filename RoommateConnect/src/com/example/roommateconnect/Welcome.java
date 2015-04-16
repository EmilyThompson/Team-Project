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
	
	private Button LoginButton;
	private Button CreateAccountButton;
	private static final String tag = "Welcome page";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		Linear = (LinearLayout) findViewById(R.id.linearLayout);
		Toast.makeText(getApplicationContext(), "Creating Table", Toast.LENGTH_SHORT).show();
		
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
			Intent wL = new Intent();
			wL.setComponent(new ComponentName("com.example.roommateconnect",
					"com.example.roommateconnect.LoginPage"));
			startActivity(wL);
			break;
		case R.id.CreateAccountButton:
			Log.i(tag, "Create acount Button Clicked");
			Intent wA = new Intent();
			wA.setComponent(new ComponentName("com.example.roommateconnect",
					"com.example.roommateconnect.AccountInformation"));
			startActivity(wA);
			break;
		}
	}
}
