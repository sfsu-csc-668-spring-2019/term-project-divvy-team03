package com.example.divvy.models;

import java.net.URL;

public class Message {
    private String text;
    private String sender;
    private URL imageUrl;

    public Message(String text, String sender, URL imageUrl){
        this.text = text;
        this.sender = sender;
        this.imageUrl = imageUrl;
    }

    public String getMessage(){
        return this.text;
    }

    public String getSender(){
        return this.sender;
    }

    public URL getImageUrl(){
        return this.imageUrl;
    }
}