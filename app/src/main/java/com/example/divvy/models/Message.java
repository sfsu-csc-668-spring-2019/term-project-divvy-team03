package com.example.divvy.models;

public class Message {
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
}