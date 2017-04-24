package com.example.bookandquote;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
/**
 * Created by ASUS on 4/24/2017.
 */

public class QuotesDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_QUOTE};


    public QuotesDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Quote createQuote(String quote){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_QUOTE, quote);

        long insertId = database.insert(MySQLiteHelper.TABLE_QUOTES, null, values);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_QUOTES, allColumns, MySQLiteHelper.COLUMN_ID + " = " +
            insertId, null, null, null, null);

        cursor.moveToFirst();
        Quote newQuote = cursorToQuote(cursor);
        return newQuote;
    }

    public void deleteQuote(Quote quote){
        long id = quote.getId();
        System.out.println("Quote deleted with id: " + id);

        database.delete(MySQLiteHelper.TABLE_QUOTES, MySQLiteHelper.COLUMN_ID + " = " + id, null);

    }

    public List<Quote> getAllQuote(){
        List<Quote> quotes = new ArrayList<Quote>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_QUOTES,allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Quote quote = cursorToQuote(cursor);
            quotes.add(quote);
            cursor.moveToNext();
        }

        cursor.close();
        return quotes;
    }

    private Quote cursorToQuote(Cursor cursor){
        Quote quote = new Quote();
        quote.setId(cursor.getLong(0));
        quote.setQuote(cursor.getString(1));
        return quote;

    }
}
