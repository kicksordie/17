package com.breadcrumbteam.rateagator;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;

public class SearchResults extends Activity {

	/**Identifies the name of the search query string
	 * in the intent*/
	public static final String INTENT_QUERY="query";
	
	/**Identifies the name of the list of search results
	 * in the intent*/
	public static final String INTENT_RESULTS="names";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.searchresults);
		
		//Load in search results
		Bundle b = this.getIntent().getExtras();
		ArrayList<String> searchResults = b.getStringArrayList(INTENT_RESULTS);
		
		EditText query=(EditText) findViewById(R.id.searchBar);
		query.setText(this.getIntent().getStringExtra(INTENT_QUERY));
		
		ViewGroup resultsList=(ViewGroup) findViewById(R.id.searchResultsList);
		for (String name:searchResults){
			Log.d("result list", name);
			Button currentResult=new Button(this);
			currentResult.setText(name);
			currentResult.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
			currentResult.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String text=((Button)v).getText().toString();
					Log.d("SearchResults","clicked on "+text);
					
					String lastName;
					String firstName;
					
					try{
					lastName=text.substring(0,text.indexOf(", "));
					firstName=text.substring(text.lastIndexOf(", ")+1,text.length());
					}catch (Exception E){
						Log.d("SearchResults", "could not parse the first and last name in the button");
						return;
					}
					
					goToProfessor(firstName, lastName);
					
				}
			});
			resultsList.addView(currentResult);
		}
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		((EditText)this.findViewById(R.id.searchBar)).clearFocus();
	}
	
	public void search(View view){
		String text=((EditText)this.findViewById(R.id.searchBar)).getText().toString();
		MainActivity.performSearch(view, text, this);
	}
	
	public void goToProfessor(String firstName,String lastName){
		Intent intent=new Intent(this,ProfessorPage.class);
		intent.putExtra(ProfessorPage.INTENT_FIRST_NAME, firstName);
		intent.putExtra(ProfessorPage.INTENT_LAST_NAME, lastName);
		this.startActivity(intent);
	}

}
