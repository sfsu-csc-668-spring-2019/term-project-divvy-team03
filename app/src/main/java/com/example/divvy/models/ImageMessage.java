package com.example.divvy.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class ImageMessage extends Message {

    private String image;

    public ImageMessage(String text, String sender, String image) {
        super(text, sender);
        this.image = image;
    }


    @Override
    public JSONObject toJsonFile() {
        JSONObject json = super.toJsonFile();
        try{
            json.put("image", this.image);
            return json;
        }catch(JSONException e){
            Log.d("ERROR: ", e.toString());
        }catch(NullPointerException e){
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public String getImage(){
        return this.image;
    }
}