package com.example.divvy.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageFactory {

    public static Message create(JSONObject data){
        try {
            if (data.has("imageUrl")) {
                return new ImageMessage(data.getString("message"), data.getString("senderNickname"), data.getString("imageUrl"));
            }else{
                return new Message(data.getString("message"), data.getString("senderNickname"));
            }
        }catch(JSONException e){
            Log.d("ERROR: ", e.toString());
        }
        return null;
    }
}
