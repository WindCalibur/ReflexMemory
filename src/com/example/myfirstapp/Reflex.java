package com.example.myfirstapp;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Reflex extends Activity {
	
	RelativeLayout relativeLayout;
	TextView textView; // Introductory text
	Button btn; // Start button
	Timer timer; // Timer to initiate countdown
	Button reflexbtn; // Actual button for registering the reflex response
	Button returnbtn; // Button for returning to default page
	TextView returnText; // Text for return/results page
	
	Date date;
	Date endDate;
	
	//Layouts 
	RelativeLayout.LayoutParams textrlp;
	RelativeLayout.LayoutParams btnrlp;
	RelativeLayout.LayoutParams mainrlp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		
		relativeLayout = new RelativeLayout(this);

		textView = new TextView(this);
	    textView.setTextSize(20);
	    textView.setText("Reflex page! Click the button once you see it turn red");
	    textView.setId(001); // ID = 001 for textView
	    
	    btn = new Button(this);
        btn.setText("Start");
        btn.setId(002);  // ID = 002 for btn
        
        // Start button functionality
        btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                   startReflex();
            }
        });

        // Text layout
        textrlp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.FILL_PARENT);
        textrlp.height = 80;
        
        // Button layout
        btnrlp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.FILL_PARENT);
        btnrlp.addRule(RelativeLayout.BELOW, textView.getId());
        
        // Main layout
        mainrlp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.FILL_PARENT);

        
        // add the views,
        relativeLayout.addView(textView, textrlp);
        relativeLayout.addView(btn, btnrlp);
        setContentView(relativeLayout, mainrlp);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_activity_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_search:
	            openSearch();
	            return true;
	        case R.id.action_settings:
	            openSettings();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	private void openSettings() {
		// TODO Auto-generated method stub
		
	}

	private void openSearch() {
		// TODO Auto-generated method stub
		
	}
	
	public void startReflex() {
		// Remove textview and btn while initializing reflexbtn 
		relativeLayout.removeAllViews();
		 // reflexbtn initialization
		reflexbtn = new Button(this);
        reflexbtn.setText("Wait");
        reflexbtn.setBackgroundColor(0xFF79CDCD); // FF at front is for no transparency
        reflexbtn.setId(003);  // ID = 003 for reflexbtn

        RelativeLayout.LayoutParams rbtnrlp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.FILL_PARENT);
        relativeLayout.addView(reflexbtn, rbtnrlp);
		
		// Initiation of countdown
		timer = new Timer("reflexTimer");
		TimerTask timertask = new TimerTask() {
			@Override
			public void run() {
				startCountdown();
			}
		};
		
		// Random countdown
		Random rand = new Random();
	    int countdownTime = rand.nextInt((10000 - 1000) + 1) + 1000; // randomly generate, in terms of miliseconds, the delay before countdown
		timer.schedule(timertask, countdownTime); // timertask is scheduled for countdown
	}
	
	public void startCountdown() {

		runOnUiThread(new Runnable() {
		     @Override
		     public void run() {
		    	 reflexbtn.setText("Now!");
		         reflexbtn.setBackgroundColor(0xFFC16B6B); // FF at front is for no transparency
		         
		         // reflexbtn functionality added at beginning of countdown
		         reflexbtn.setOnClickListener(new Button.OnClickListener() {
		             public void onClick(View v) {
		                    stopCountdown();
		             }
		         });
		    }
		});
		
		date = new Date();
	}
	
	public void stopCountdown() {
		endDate = new Date();
		long diff = endDate.getTime() - date.getTime();
		double doubleDiff = (double) diff; // allow decimals
		double diffSeconds = doubleDiff / 1000;
		
		// Remove all views
		relativeLayout.removeAllViews();
		
		// New text for stop counter
		returnText = new TextView(this);
	    returnText.setTextSize(20);
	    
	    returnText.setText("Your reflex is: " + diffSeconds + " seconds");
	    returnText.setId(004); // ID = 004 for returnText
		
	    // Layout for return text
	    RelativeLayout.LayoutParams textLayout = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		// Layout for button
		RelativeLayout.LayoutParams btnLayout = new RelativeLayout.LayoutParams(
		        RelativeLayout.LayoutParams.FILL_PARENT,
		        RelativeLayout.LayoutParams.FILL_PARENT);;
		        btnLayout.addRule(RelativeLayout.BELOW, returnText.getId());
		
		// Return button setup
		returnbtn = new Button(this);
        returnbtn.setText("Return");
        returnbtn.setId(005); // ID = 005 for returnbtn
        returnbtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                   reinitiate();
            }
        });
        
        relativeLayout.addView(returnText, textLayout);
        relativeLayout.addView(returnbtn, btnLayout);
	}
	
	public void reinitiate() {
		relativeLayout.removeAllViews();
		relativeLayout.addView(textView, textrlp);
        relativeLayout.addView(btn, btnrlp);
	}
}
