package com.example.roommateconnect;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AccountHome extends Activity implements OnClickListener {

	// Create Tag
	private static final String tag = "Account Home";

	// Create Database References
	private Button accountSettings;
	private Button myMatches;

	// Create String for Intent Extra
	String accountInformation;
	String sending_user_matches;

	// onCreate
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_home);

		accountSettings = (Button) findViewById(R.id.accountSettings);
		accountSettings.setOnClickListener(this);
		myMatches = (Button) findViewById(R.id.myMatches);
		myMatches.setOnClickListener(this);

		Intent intent = getIntent();
		accountInformation = intent.getStringExtra("USER1");
		Log.i(tag, "Email received by Account Home equals: " + accountInformation);
		sending_user_matches = accountInformation;
	}

	// Button Listener
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.accountSettings:
			Log.i(tag, "Account Settings button clicked");
			Intent kk = new Intent(AccountHome.this, AccountInformation.class);
			kk.putExtra("USER_ACCOUNT", accountInformation);
			startActivity(kk);
			break;
		case R.id.myMatches:
			Log.i(tag, "My Matches button clicked");
			Intent bb = new Intent(AccountHome.this, Matches.class);
			bb.putExtra("USER_MATCHES", sending_user_matches);
			startActivity(bb);
			Log.i(tag, "My Matches Pressed!");
			break;
		}
	}
}
