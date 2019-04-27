package com.example.divvy.Controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.divvy.R;
import com.example.divvy.models.ChatBoxAdapter;
import com.example.divvy.models.Message;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;


public class MessagingActivity extends AppCompatActivity {

    private EditText textField;
    private Button sendButton;
    private RecyclerView recyclerView;
    private ImageView addImageButton;
    private TextView listingName;
    private Socket socket;
    private String username;
    private URI uri;
    private String listing_id;
    private List<Message> messageList;
    private ChatBoxAdapter chatBoxAdapter;
    private Emitter.Listener messageListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        setUpUi();
        setUpListeners();
        username = getIntent().getExtras().getString(MainActivity.USERNAME);
    }

    @Override
    protected void onDestroy() {
        socket.disconnect();
        socket.close();
        super.onDestroy();
    }

    private boolean setUpSocket(){
        try{
            socket = IO.socket(uri);
            socket.connect();
            socket.on("message", messageListener);
            return true;
        }catch(URISyntaxException e){
            e.printStackTrace();
            return false;
        }
    }

    private void setUpUi(){
        textField = findViewById(R.id.text);
        sendButton = findViewById(R.id.send_button);
        recyclerView = findViewById(R.id.chat_box);
        addImageButton = findViewById(R.id.add_image_button);
        listingName = findViewById(R.id.listing_name);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void setUpListeners(){
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ADD IMAGE", "CLICKED");
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textField.getText().toString().trim() != ""){
                    socket.emit(listing_id, "message", textField.getText().toString().trim(), username);
                    textField.setText("");
                }
            }
        });

        messageListener = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try{
                    String username = data.getString("username");
                    String message = data.getString("message");
                    URL imageUrl = new URL(data.getString("imageUrl"));
                    Message m = new Message(message, username, imageUrl);
                    messageList.add(m);
                    chatBoxAdapter = new ChatBoxAdapter(messageList);
                    chatBoxAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(chatBoxAdapter);
                }catch (JSONException e){
                    e.printStackTrace();
                }catch (java.net.MalformedURLException e){
                    e.printStackTrace();
                }
            }
        };
    }



}
