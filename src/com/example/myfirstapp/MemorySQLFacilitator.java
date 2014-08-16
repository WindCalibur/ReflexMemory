package com.example.myfirstapp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MemorySQLFacilitator {

	  // Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
	      MySQLiteHelper.COLUMN_QUESTION, MySQLiteHelper.COLUMN_ANSWER };

	  public MemorySQLFacilitator(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public MemoryCard createMemory(String question, String answer) {
	    ContentValues values = new ContentValues();
	    values.put(MySQLiteHelper.COLUMN_QUESTION, question);
	    values.put(MySQLiteHelper.COLUMN_ANSWER, answer);
	    long insertId = database.insert(MySQLiteHelper.TABLE_COMMENTS, null,
	        values);
	    Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
	        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    MemoryCard newMemoryCard = cursorToMemoryCard(cursor);
	    cursor.close();
	    return newMemoryCard;
	  }

	  public void deleteCard(MemoryCard card) {
	    long id = card.getId();
	    System.out.println("Comment deleted with id: " + id);
	    database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
	        + " = " + id, null);
	  }

	  public List<MemoryCard> getAllCards() {
	    List<MemoryCard> cards = new ArrayList<MemoryCard>();

	    Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      MemoryCard card = cursorToMemoryCard(cursor);
	      cards.add(card);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return cards;
	  }

	  private MemoryCard cursorToMemoryCard(Cursor cursor) {
	    MemoryCard card = new MemoryCard();
	    card.setId(cursor.getLong(0));
	    card.setQuestion(cursor.getString(1));
	    card.setAnswer(cursor.getString(2));
	    return card;
	  }
	} 

