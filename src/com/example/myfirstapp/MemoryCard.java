package com.example.myfirstapp;

public class MemoryCard {
	  private long id;
	  private String question;
	  private String answer;

	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }

	  public String getQuestion() {
	    return question;
	  }

	  public void setQuestion(String question) {
	    this.question = question;
	  }
	  
	  public String getAnswer() {
		  return answer;
	  }

	  public void setAnswer(String answer) {
		  this.answer = answer;
	  }

	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return question;
	  }
	} 
