package com.example.divvy.models;

public class imageMessage extends Message {

    private String url;

    public imageMessage() {
        super("", "");
    }

    public imageMessage(String text, String sender, String url) {
        super(text, sender);
        this.url = url;
    }

    public String getStringUrl(){
        return this.url;
    }


}