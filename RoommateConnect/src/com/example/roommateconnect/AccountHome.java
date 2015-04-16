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

public class AccountHome extends Activity implements OnClickListener {
	
	private Button accountSettings;
	private Button myMatches;
	private static final String tag = "Account Home";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_home);
	
	accountSettings = (Button) findViewById(R.id.accountSettings);
	accountSettings.setOnClickListener(this);
	myMatches = (Button)findViewById(R.id.myMatches);
	myMatches.setOnClickListener(this);
	}
	
	public void onClick(View v){
	switch (v.getId()){
	case R.id.accountSettings:
		Intent kk = new Intent(AccountHome.this, AccountInformation.class);
	//	kk.setComponent(new ComponentName("com.example.roommateconnect", "com.example.roommateconnect.accountinformation"));
		startActivity(kk);
		break;
	case R.id.myMatches:
		Log.i(tag, "My Matches Pressed!");
		break;
	}
	}
}
