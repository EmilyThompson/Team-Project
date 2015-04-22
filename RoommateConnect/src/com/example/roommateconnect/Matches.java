package com.example.roommateconnect;

import java.util.ArrayList;

import com.example.roommateconnect.R;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Font;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.R.color;

public class Matches extends ListActivity {
	// Create tag
	private static final String tag = "Matches";

	// Create Database References
	SQLiteDatabase db;
	private static String DBName = "MATCHES.db";
	private static String Table = "MY_TABLE";
	private Cursor curse1;
	private Cursor curse2;

	// Create Imported Data Strings
	private String LOOKINGFOR;
	private String QUANTITY;

	// Create Button
	private// Create String for Intent Extra
	String matches;

	// Create Strings
	private String oppLookingFor;

	// Create Match Strings
	private String matchID;
	private String matchFirstName;
	private String matchLastName;
	private String matchPhone;
	private String matchEmail;
	private String matchPassword;
	private String matchSex;
	private String matchClassYear;
	private String matchLookingFor;
	private String matchQuantity;
	
	// Create Array
	ArrayList<String> Matches = new ArrayList<String>();
	private ArrayAdapter<String> aa;
	String [] title = {"Name", "Info"};
	
	// Create View
	final int PICK1_Email = Menu.FIRST + 1;
	final int PICK2_Call = Menu.FIRST + 2;
	final int PICK3_Text = Menu.FIRST + 3;
	View VIEW;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_matches);
		
		// Get & Store Message from Intent
		Intent intent = getIntent();
		String match = intent.getStringExtra("USER_MATCHES");
		Log.i(tag, "Matches intent equals " + match);

		getMatches(match);
		aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Matches);
		
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		//getListView().setSelector(R.color.Red); //This isnt working
		VIEW = new View(this);
		setListAdapter(aa);
	}

	private void getMatches(String userString) {
		Log.i(tag, "Parameter sent through loadMatches equals " + userString);
		try {
			db = openOrCreateDatabase(DBName, Context.MODE_PRIVATE, null);
			Cursor cursor = db.rawQuery("SELECT " + "LookingFor, GroupAmount "
					+ "FROM " + Table + " WHERE Email='" + userString + "'",
					null);

			int number = cursor.getCount();
			Log.i(tag, "There are " + number + " customer accounts with Email="
					+ userString);

			cursor.moveToFirst();
			LOOKINGFOR = cursor.getString(cursor.getColumnIndex("LookingFor"));
			QUANTITY = cursor.getString(cursor.getColumnIndex("GroupAmount"));

			Log.i(tag, LOOKINGFOR);
			Log.i(tag, QUANTITY);

			if (LOOKINGFOR.equals("Room(s)")) {
				oppLookingFor = "Roommate(s)";
				Log.i(tag, "Match Looking for must equal " + oppLookingFor);
			} else if (LOOKINGFOR.equals("Roommate(s)")) {
				oppLookingFor = "Room(s)";
				Log.i(tag, "Match Looking for must equal " + oppLookingFor);
			}

			Cursor cursor2 = db.rawQuery("SELECT * FROM " + Table
					+ " WHERE GroupAmount='" + QUANTITY + "' AND LookingFor='"
					+ oppLookingFor + "'", null);

			int count = cursor2.getCount();
			Log.i(tag, "There are " + count + " matches.");

			cursor2.moveToFirst();
			int position = cursor2.getPosition();
			Log.i(tag, "Move to first results in cursor position = " + position);

			for (cursor2.moveToFirst(); !(cursor.getPosition() == count - 1); cursor2
					.moveToNext()) {
				matchFirstName = cursor2.getString(cursor2
						.getColumnIndex("FirstName"));
				matchLastName = cursor2.getString(cursor2
						.getColumnIndex("LastName"));
				matchPhone = cursor2.getString(cursor2
						.getColumnIndex("PhoneNumber"));
				matchEmail = cursor2.getString(cursor2.getColumnIndex("Email"));
				matchSex = cursor2.getString(cursor2.getColumnIndex("Sex"));
				matchClassYear = cursor2.getString(cursor2
						.getColumnIndex("ClassLevel"));

				Log.i(tag, "Results name: Name=" + matchFirstName + " "
						+ matchLastName + "...Phone= " + matchPhone
						+ "...Email= " + matchEmail + "...Sex= " + matchSex
						+ "...ClassLevel= " + matchClassYear);
				position = cursor2.getPosition();
				Log.i(tag, "At end of loop cursor position is " + position);
				
				
				String info = matchFirstName + " " + matchLastName + 
						"\n Sex: " + matchSex + "     Class: " + matchClassYear;
				Matches.add(info);		
				if (cursor2.getPosition() == count - 1)
					break;
			}
		} catch (Exception e) {
			Log.i(tag, "Error getting matches.");
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

			super.onCreateOptionsMenu(menu);
			MenuItem item1 = menu.add(0, PICK1_Email, Menu.NONE, "Email Match");
			MenuItem item2 = menu.add(0, PICK2_Call, Menu.NONE, "Call Match");
			MenuItem item3 = menu.add(0, PICK3_Text, Menu.NONE, "Text Match");
			
			return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {		
	    
	    int itemID = item.getItemId();
	    
	    switch (itemID) {
	    case PICK1_Email : 
	    	Toast.makeText(this, "Emailing Match", Toast.LENGTH_SHORT).show();
	    	Log.i(tag, "Emailing Match");
	    	Intent e = new Intent(Intent.ACTION_SENDTO);
	    	e.putExtra(Intent.EXTRA_EMAIL, matchEmail);
	    	e.putExtra(Intent.EXTRA_SUBJECT, "Roommate Connect Inquiry");
	    	startActivity(e);
	      	break;
	    case PICK2_Call : 
	    	Toast.makeText(this, "Calling Match", Toast.LENGTH_SHORT).show();
	    	Log.i(tag, "Calling Match");
	    	Uri dUri = Uri.parse("tel:"+matchPhone);
	    	Intent c = new Intent(Intent.ACTION_CALL, dUri);
	    	startActivity(c);
	    	break;
	    case PICK3_Text : 
	    	Toast.makeText(this, "Texting Match", Toast.LENGTH_SHORT).show();
	    	Log.i(tag, "Texting Match");
	    	Intent t = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", matchPhone, null));
	    	startActivity(t);
	    	break;
	    }
		return false;
	}
}
