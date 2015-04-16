package com.example.roommateconnect;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginPage extends Activity implements OnClickListener {
	
	private Button login;
	private EditText username;
	private EditText password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_page);
		
		login = (Button) findViewById(R.id.loginButtonLP);
		login.setOnClickListener(this);
		
		username = (EditText)findViewById(R.id.EnterUsernameLP);
		password = (EditText)findViewById(R.id.EnterPasswordLP);
		
	}
	
	public void onClick(View v){
	
	String user = username.getText().toString();
	String pass = password.getText().toString();
	
	Intent it = new Intent();
	it.setComponent(new ComponentName("com.example.roommateconnect", "com.example.roommateconnect.AccountHome"));
	startActivity(it);
	}
}
