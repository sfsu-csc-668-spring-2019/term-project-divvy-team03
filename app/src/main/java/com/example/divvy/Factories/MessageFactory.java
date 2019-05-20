package com.example.divvy.Factories;

import android.util.Log;

import com.example.divvy.models.Message;

import org.json.JSONException;
import org.json.JSONObject;


public class MessageFactory {

    public static Message create(JSONObject data){
        JSONObject d = data;
        try {
            if (data.has("image")) {
                return new Message(data.getString("message"), data.getString("senderNickname"), data.getString("image"));
            }else{
                return new Message(data.getString("message"), data.getString("senderNickname"));
            }
        }catch(JSONException e){
            try {
                if (data.has("image")) {
                    return new Message(data.getString("message"), data.getString("sender"), data.getString("image"));
                } else {
                    return new Message(data.getString("message"), data.getString("sender"));
                }
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }
        return null;
    }

    public static Message create(String messageText, String username, String bitmap){
        return new Message(messageText, username, bitmap);
    }

    public static Message create(String messageText, String username){
        return new Message(messageText, username);
    }

    public static JSONObject toJsonFile(Message message){
        JSONObject json = new JSONObject();
        try{
            json.put("message", message.getMessage());
            json.put("senderNickname", message.getSender());
            if(!message.getImage().equals("")) {json.put("image", message.getImage());}
            return json;
        }catch(JSONException e){
            Log.d("ERROR: ", e.toString());
        }
        return null;
    }





}
