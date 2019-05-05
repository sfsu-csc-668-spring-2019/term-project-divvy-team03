package com.example.divvy.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
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
import com.example.divvy.Controllers.imageSelectorController.*;
import com.example.divvy.models.ChatBoxAdapter;
import com.example.divvy.models.Message;
import com.example.divvy.models.imageMessage;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.engineio.client.Transport;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Manager;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.divvy.Controllers.imageSelectorController.getBitmapForUri;
import static com.example.divvy.Controllers.imageSelectorController.savePhotoImage;
import static com.example.divvy.Controllers.imageSelectorController.scaleImage;
import static com.example.divvy.Controllers.imageSelectorController.selectImage;


public class MessagingActivity extends AppCompatActivity {

    private EditText textField;
    private Button sendButton;
    private RecyclerView recyclerView;
    private ImageView addImageButton;
    private TextView listingName;
    private Socket socket;
    private String username = "username";
    private URI uri;
    private String listing_id;
    private List<Message> messageList;
    private ChatBoxAdapter chatBoxAdapter;
    private Emitter.Listener messageListener;
    private double MAX_LINEAR_DIMENSION = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        setUpUi();
        setUpListeners();
        setUpSocket();
        messageList = new ArrayList<>();
        //username = getIntent().getExtras().getString(MainActivity.USERNAME);
    }

    @Override
    protected void onDestroy() {
        destroySocket();
        super.onDestroy();
    }

    private void destroySocket(){
        socket.disconnect();
        socket.close();

    }

    private boolean setUpSocket() {
        try {
            socket = IO.socket("http://34.226.139.149/");
            socket = socket.connect();
            socket.emit("join", username);
            socket.on("message", messageListener);




            Log.d("socket", Boolean.toString(socket.connected()));
            return true;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
            return false;
        }
    }

    private void setUpUi() {
        textField = findViewById(R.id.text);
        sendButton = findViewById(R.id.send_button);
        recyclerView = findViewById(R.id.chat_box);
        addImageButton = findViewById(R.id.add_image_button);
        listingName = findViewById(R.id.listing_name);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void setUpListeners() {
        addImageButton.setOnClickListener(view -> {
            selectImage(this);
        });

        sendButton.setOnClickListener(view -> {
            if (!textField.getText().toString().trim().equals("")) {
                socket.emit("messagedetection", textField.getText().toString().trim(), username);
                textField.setText("");
            }
        });

        messageListener = args -> {
            JSONObject data = (JSONObject) args[0];
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String username = data.getString("senderNickname");
                        String message = data.getString("message");
                        Message m;
                        if(data.has("imageUrl")){
                            String imageUrl = data.getString("imageUrl");
                            m = new imageMessage(message, username, imageUrl);
                        }else{
                            m = new Message(message, username);
                        }
                        messageList.add(m);
                        chatBoxAdapter = new ChatBoxAdapter(messageList);
                        chatBoxAdapter.notifyDataSetChanged();
                        Log.d("New Message", username + " : " + message);
                        recyclerView.setAdapter(chatBoxAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            if(data != null){
                Uri uri = data.getData();
                Bitmap bitmap = getBitmapForUri(this.getContentResolver(), uri);
                Bitmap resizedBitmap = scaleImage(bitmap);
                if(bitmap != resizedBitmap){
                    savePhotoImage(resizedBitmap, this);
                    bitmap = resizedBitmap;
                }
                addImageButton.setImageDrawable(new BitmapDrawable(getResources(), bitmap));
            }else{
                Log.d("ERROR: ", "Unable to get image for uploading");
            }

        }
    }

}
