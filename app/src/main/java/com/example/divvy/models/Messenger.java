package com.example.divvy.models;


import android.util.Log;

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

    public Messenger(String username, Observer observer) {
      this.username = username;
      this.addObserver(observer);
      setUpChannels();
      setUpSocket();
    }

    public void endSession() {
        socket.disconnect();
        socket.close();
    }

    public void sendMessage(Message message) {
        if(message != null) socket.emit("messagedetection", message.toJsonFile());
    }

    private boolean setUpSocket() {
        try {
            socket = IO.socket("http://34.226.139.149/").connect();
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