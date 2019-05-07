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


public class MessagingActivity extends AppCompatActivity implements Observer {

    private EditText textField;
    private Button sendButton;
    private RecyclerView recyclerView;
    private ImageView addImageButton, cancelButton;
    private TextView listingName;

    private String username = "username";
    private URI uri;
    private String listing_id;
    private List<Message> messageList;
    private ChatBoxAdapter chatBoxAdapter;
    private double MAX_LINEAR_DIMENSION = 500;

    private Messenger messenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        setUpUi();
        setUpListeners();

        messageList = new ArrayList<>();
        messenger = new Messenger(username, this);
        //username = getIntent().getExtras().getString(MainActivity.USERNAME);
    }

    @Override
    protected void onDestroy() {
        destroySocket();
        super.onDestroy();
    }

    private void destroySocket(){
        messenger.endSession();
    }

    private void setUpUi() {
        textField = findViewById(R.id.text);
        sendButton = findViewById(R.id.send_button);
        recyclerView = findViewById(R.id.chat_box);
        addImageButton = findViewById(R.id.add_image_button);
        listingName = findViewById(R.id.listing_name);
        cancelButton = findViewById(R.id.cancel_button);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        clearImageButton();
    }

    private void clearImageButton(){
        addImageButton.setImageResource(R.drawable.add_icon);
        cancelButton.setVisibility(View.INVISIBLE);
        cancelButton.setClickable(false);

    }

    private void setUpListeners() {
        addImageButton.setOnClickListener(view ->
            selectImage(this)
        );

        cancelButton.setOnClickListener(view ->
            clearImageButton()
        );

        sendButton.setOnClickListener(view -> {
            if (cancelButton.getVisibility() == View.VISIBLE){
                clearImageButton();
                //send image
            }

            String message = textField.getText().toString().trim();

            if (!message.equals("")) {
                //send normal message
                // TODO: This probably belongs in Messenger?
                messenger.sendMessage(message);
                textField.setText("");
            }
        });
    }

    public void update(Observable o, Object arg) {
        messageList.add((Message) arg);

        chatBoxAdapter = new ChatBoxAdapter(messageList);
        recyclerView.setAdapter(chatBoxAdapter);
        chatBoxAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(messageList.size() - 1);
        Log.d("new message", Integer.toString(messageList.size()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO: Create BitmapLoader class?
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            if(data != null){
                Uri uri = data.getData();
                Bitmap bitmap = getBitmapForUri(this.getContentResolver(), uri);
                Bitmap resizedBitmap = scaleImage(bitmap);
                if(bitmap != resizedBitmap){
                    savePhotoImage(resizedBitmap, this);
                    bitmap = resizedBitmap;
                }
                addImageButton.setImageBitmap(bitmap);
                cancelButton.setVisibility(View.VISIBLE);
                cancelButton.setClickable(true);
            }else{
                Log.d("ERROR: ", "Unable to get image for uploading");
            }

        }
    }

}
