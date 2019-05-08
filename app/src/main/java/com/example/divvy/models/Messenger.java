package com.example.divvy.models;

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
            e.printStackTrace();
            return false;
        }
    }

    private void setUpChannels() {
        messageListener = args -> {
            JSONObject data = (JSONObject) args[0];
            this.setChanged();
            this.notifyObservers(MessageFactory.create(data));
        };
    }
}