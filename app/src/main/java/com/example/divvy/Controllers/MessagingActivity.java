package com.example.divvy.Controllers;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.divvy.R;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URI;
import java.net.URISyntaxException;


public class MessagingActivity extends AppCompatActivity {

    private EditText textField;
    private Button sendButton;
    private RecyclerView recyclerView;
    private ImageView addImageButton;
    private TextView listingName;
    private Socket socket;
    private String username;
    private URI uri;
    private int listing_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        setUpUi();
        setUpListeners();
        username = getIntent().getExtras().getString(MainActivity.USERNAME);
    }

    private boolean setUpSocket(){
        try{
            socket = IO.socket(uri);
            socket.connect();

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
                Log.d("SEND BUTTON", "CLICKED");
            }
        });
    }

    @Override
    protected void onDestroy() {
        socket.disconnect();
        socket.close();
        super.onDestroy();
    }
}
