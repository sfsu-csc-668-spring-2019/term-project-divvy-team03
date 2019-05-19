package com.example.divvy.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public  class Message {
    protected String text, sender, image = "";

    public Message(String text, String sender){
        this.text = text;
        this.sender = sender;
    }

    public Message(String text, String sender, String image){
        this.text = text;
        this.sender = sender;
        this.image = image;
    }

    public String getMessage(){
        return this.text;
    }

    public String getSender(){
        return this.sender;
    }

    public String getImage(){ return this.image;}





}