package com.example.myfirstapp;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Memory extends Activity {
	  private MemorySQLFacilitator datasource;
	  private int index = 0;
	  private List<MemoryCard> allCards = new ArrayList<MemoryCard>();
	  public int questionAnswer = 1;

	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_memory);

	    datasource = new MemorySQLFacilitator(this);
	    datasource.open();

	    List<MemoryCard> values = datasource.getAllCards();

	  }
	  
	  public void addCardPage(View view) {
		  setContentView(R.layout.add_card);
	  }

	  public void addCard(View view) {
		  
		  // get text fields first
		  EditText editText = (EditText) findViewById(R.id.question);
		  String question = editText.getText().toString();
		  editText = (EditText) findViewById(R.id.answer);
		  String answer = editText.getText().toString();
		  
		  // add to database
		  MemoryCard memoryCard = datasource.createMemory(question, answer);
		  setContentView(R.layout.activity_memory);
	  }
	  
	  
	  //
	  //  Viewing card and deleting Card
	  //
	  //
	  
	  
	  public void viewCardPage(View view) {

		  setContentView(R.layout.read_card);
		  allCards = datasource.getAllCards();
		  MemoryCard currentCard = allCards.get(index);	
		  String question = currentCard.getQuestion();
		  String answer = currentCard.getAnswer();
		  
		  TextView questionView = (TextView)findViewById(R.id.questionview);
		  questionView.setTextSize(20);
		  if (questionAnswer == 1) {
			  questionView.setText(question);
		  } else {
			  questionView.setText(answer);
		  }
		  
		  
		  // button for switching between question and answer
		  Button togglebtn = (Button)findViewById(R.id.togglebtn);
		  if (questionAnswer == 1) {
			  togglebtn.setText("Answer");
		  } else { 
			  togglebtn.setText("Question");
		  }
	      togglebtn.setOnClickListener(new Button.OnClickListener() {
	            public void onClick(View v) {
	            	displayAnswerOrQuestion();
	            }
	      });

		  
	  }

	  public void previousCard(View view) {
		  if (index != 0) {
			  index--;
		  }
		  viewCardPage(findViewById(R.id.view_card_main));
	  }

	  public void nextCard(View view) {
		  if (index != (allCards.size() - 1)) {
			  index++;
		  }
		  viewCardPage(findViewById(R.id.view_card_main));
	  }

	  public void deleteCard(View view) {
		  datasource.deleteCard(allCards.get(index));
		  index = 0;
		  viewCardPage(findViewById(R.id.view_card_main));
	  }
	  
	  public void returnToMemory(View view) {
		  setContentView(R.layout.activity_memory);
		  index = 0;
	  }
	  
	  public void displayAnswerOrQuestion() {
		  if (questionAnswer == 1) {
			  questionAnswer = 2;
			  viewCardPage(findViewById(R.id.view_card_main));
		  } else {
			  questionAnswer = 1;
			  viewCardPage(findViewById(R.id.view_card_main));
		  }
	  }

	  @Override
	  protected void onResume() {
	    datasource.open();
	    super.onResume();
	  }

	  @Override
	  protected void onPause() {
	    datasource.close();
	    super.onPause();
	  }

	} 
