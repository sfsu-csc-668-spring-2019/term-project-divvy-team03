package com.example.divvy.models;

public class ImageMessage extends Message {

    private String url;

    public ImageMessage() {
        super("", "");
    }

    public ImageMessage(String text, String sender, String url) {
        super(text, sender);
        this.url = url;
    }

    public String getStringUrl(){
        return this.url;
    }

    public void render() {
        // Do rendering stuff
    }
}