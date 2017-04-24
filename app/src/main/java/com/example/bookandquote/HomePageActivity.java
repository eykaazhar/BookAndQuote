package com.example.bookandquote;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.content.ContentValues;
import android.content.CursorLoader;

import android.database.Cursor;

import android.view.Menu;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ASUS on 4/23/2017.
 */


public class HomePageActivity extends Activity{
    private QuotesDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        dataSource = new QuotesDataSource(this);
        dataSource.open();

        List<Quote> values = dataSource.getAllQuote();

        ArrayAdapter<Quote> adapter = new ArrayAdapter<Quote>(this, android.R.layout.simple_list_item_1, values);
        getListView().setAdapter(adapter);
        //setListAdapter(adapter);
    }

    public void onClickAddQuote(View view){
        ContentValues values = new ContentValues();
        values.put(QuoteProvider.QUOTE, ((EditText)findViewById(R.id.quoteText)).getText().toString());

        Uri uri = getContentResolver().insert(QuoteProvider.CONTENT_URI, values);
        Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
    }

    public void onClickRetrieveQuote(View view){
        String URL = "content://com.example.bookandquote.QuoteProvider";

        Uri quote = Uri.parse(URL);
        Cursor c = managedQuery(quote, null, null, null, "quote");

        if(c.moveToFirst()){
            do{
                Toast.makeText(this, c.getString(c.getColumnIndex(QuoteProvider._ID)) +
                ", " + c.getString(c.getColumnIndex(QuoteProvider.QUOTE)),
                        Toast.LENGTH_LONG).show();
            }while(c.moveToNext());
        }
    }

    public void onClick(View view){
        ArrayAdapter<Quote> adapter = (ArrayAdapter<Quote>) getListView().getAdapter();
        Quote quote = null;
        switch (view.getId()){
            case R.id.addQuoteButton:
                String[] quotes = new String[]{
                        "No Regret", "Live to the Fullest", "Fairy Tale"};

                int nextInt = new Random().nextInt(3);

                quote = dataSource.createQuote(quotes[nextInt]);
                adapter.add(quote);
                break;
            case R.id.deleteQuoteButton:
                if(getListView().getAdapter().getCount() > 0){
                    quote = (Quote)getListView().getAdapter().getItem(0);
                    dataSource.deleteQuote(quote);
                    adapter.remove(quote);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume(){
        dataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause(){
        dataSource.close();
        super.onPause();
    }

    public ListView getListView(){
        ListView list = (ListView)findViewById(android.R.id.list);
        return list;
    }
}
