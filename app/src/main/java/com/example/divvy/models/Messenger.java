package com.example.divvy.models;



import android.util.Log;

import com.example.divvy.Factories.MessageFactory;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.util.Observable;
import java.util.Observer;

public class Messenger extends Observable {
    private Socket socket;
    private String username;
    private Emitter.Listener messageListener;
    private Long listing_id;

    public Messenger(String username, Observer observer, Long listing_id) {
      this.username = "LORENZO";
      this.addObserver(observer);
      this.listing_id = listing_id;
      setUpChannels();
      setUpSocket();
    }

    public void endSession() {
        socket.disconnect();
        socket.close();
    }

    public void sendMessage(Message message) {
        if(message != null) socket.emit("messagedetection", MessageFactory.toJsonFile(message));
    }

    private boolean setUpSocket() {
        try {
            IO.Options opts = new IO.Options();
            opts.forceNew = true;
            opts.query = "room=" + Long.toString(listing_id);
            socket = IO.socket("http://34.226.139.149", opts).connect();
            //String params = "room=" + Long.toString(listing_id);
           // System.out.println(params);
           // "http://34.226.139.149/
            //socket = IO.socket("http://34.226.139.149:3000?"+ params).connect();
           // System.out.println("http://34.226.139.149?" + params);

            socket.emit("join", username);
            socket.on("message", messageListener);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void setUpChannels() {
        messageListener = args -> {
            JSONObject data = (JSONObject) args[0];
            try {
                this.setChanged();
                this.notifyObservers(MessageFactory.create((JSONObject) data.get("senderNickname")));
            }catch(Exception e){

            }

        };
    }
}