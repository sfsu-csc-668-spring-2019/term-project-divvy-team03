package com.example.divvy.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public  class Message {
    protected String text;
    protected String sender;

    public Message(String text, String sender){
        this.text = text;
        this.sender = sender;
    }

    public String getMessage(){
        return this.text;
    }

    public String getSender(){
        return this.sender;
    }

    public JSONObject toJsonFile(){
        JSONObject json = new JSONObject();
        try{
            json.put("message", this.text);
            json.put("senderNickname", this.sender);
            return json;
        }catch(JSONException e){
            Log.d("ERROR: ", e.toString());
        }
        return null;
    }



}