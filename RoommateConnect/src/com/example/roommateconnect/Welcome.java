package com.example.roommateconnect;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Welcome extends Activity implements OnClickListener {
	
	private Button LoginButton;
	private Button CreateAccountButton;
	private static final String tag = "Welcome page";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		
		LoginButton = (Button) findViewById(R.id.LoginButton);
		LoginButton.setOnClickListener(this);
		
		CreateAccountButton = (Button) findViewById(R.id.CreateAccountButton);
		CreateAccountButton.setOnClickListener(this);
		
	}
	
	public void onClick(View v) {
	
	switch (v.getId()){
	case R.id.LoginButton:
		Log.i(tag, "Login Button Clicked");
		Intent wL = new Intent();
		wL.setComponent(new ComponentName("com.example.roommateconnect", "com.example.roommateconnect.LoginPage"));
		startActivity(wL);
	break;
	case R.id.CreateAccountButton:
		Log.i(tag, "Create acount Button Clicked");
		Intent wA = new Intent();
		wA.setComponent(new ComponentName("com.example.roommateconnect", "com.example.roommateconnect.AccountInformation"));
		startActivity(wA);
	break;
	}
	}
}
