package com.example.bookandquote;

/**
 * Created by ASUS on 4/24/2017.
 */

public class Quote {
    private long id;
    private String quote;

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getQuote(){
        return quote;
    }

    public void setQuote(String quote){
        this.quote = quote;
    }

    @Override
    public String toString(){
        return quote;
    }
}
