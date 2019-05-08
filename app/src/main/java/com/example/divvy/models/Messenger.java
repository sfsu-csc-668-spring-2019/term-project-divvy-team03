package com.example.divvy.models;
import android.app.Activity;
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
      setUpSocket();
      setUpChannels();
    }

    public void endSession() {
      socket.disconnect();
      socket.close();
    }

    public void sendMessage(String message) {
      socket.emit("messagedetection", message, username);
    }

    private boolean setUpSocket() {
        try {
            socket = IO.socket("http://34.226.139.149/").connect();
            socket.emit("join", username);
            socket.on("message", messageListener);
            return true;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
            return false;
        }
    }

    private void setUpChannels() {
        messageListener = args -> {
            Log.d("FUCK", "message received");
            JSONObject data = (JSONObject) args[0];
            this.notifyObservers(MessageFactory.create(data));
        };
    }
}