package com.example.divvy.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import com.example.divvy.Controllers.Services.ChatHistoryService;
import com.example.divvy.Controllers.Services.NetworkReceiver;
import com.example.divvy.Controllers.helpers.LoginAuthenticator;
import com.example.divvy.R;
import com.example.divvy.models.Message;
import com.example.divvy.Factories.MessageFactory;
import com.example.divvy.models.Messenger;
import com.example.divvy.Controllers.helpers.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static com.example.divvy.Controllers.ImageSelect.encodeImage;
import static com.example.divvy.Controllers.ImageSelect.getBitmap;
import static com.example.divvy.Controllers.ImageSelect.selectImage;

public class MessagingController extends AppCompatActivity implements Observer,  NetworkReceiver.DataReceiver {

    private EditText textField;
    private Button sendButton;
    private RecyclerView recyclerView;
    private ImageView addImageButton, cancelButton;
    private TextView listingName;
    private String username;
    private List<Message> messageList;
    private RecyclerViewAdapter chatBoxAdapter;
    private NetworkReceiver mReceiver;

    private Messenger messenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        setUpUi();
        setUpListeners();
        messageList = new ArrayList<>();
        Intent intent = getIntent();
        listingName.setText(intent.getStringExtra("title"));
        username = LoginAuthenticator.getInstance().getUser(this);
        messenger = new Messenger(username, this, getIntent().getExtras().getLong("id"));
        mReceiver = new NetworkReceiver(new Handler(Looper.getMainLooper()), this);
        ChatHistoryService.GetChatHistoryById(this, mReceiver,  getIntent().getExtras().getLong("id"));
    }

    @Override
    protected void onDestroy() {
        messenger.endSession();
        super.onDestroy();
    }

    private void setUpUi() {
        textField = findViewById(R.id.text);
        sendButton = findViewById(R.id.send_button);
        recyclerView = findViewById(R.id.chat_box);
        addImageButton = findViewById(R.id.add_image_button);
        listingName = findViewById(R.id.listing_name);
        cancelButton = findViewById(R.id.cancel_button);
        NavBarController.setUpListners(findViewById(R.id.navigation), this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        listingName.setText(getIntent().getExtras().getString("title"));
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
            String messageText = textField.getText().toString().trim();
            if (cancelButton.getVisibility() == View.VISIBLE) {
                messenger.sendMessage(MessageFactory.create(messageText, username, encodeImage(((BitmapDrawable)addImageButton.getDrawable()).getBitmap())));
                clearImageButton();
                textField.setText("");
            }else if (!messageText.equals("")) {
                // TODO: This probably belongs in Messenger?
                messenger.sendMessage(MessageFactory.create(messageText, username));
                textField.setText("");
            }
        });
    }

    @Override
    public void update(Observable observable, Object o) {
        runOnUiThread( () -> {
            messageList.add((Message) o);
            chatBoxAdapter = new RecyclerViewAdapter(messageList);
            recyclerView.setAdapter(chatBoxAdapter);
            chatBoxAdapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(messageList.size() - 1);
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            if(data != null){
                addImageButton.setImageBitmap(getBitmap(data, this));
                cancelButton.setVisibility(View.VISIBLE);
                cancelButton.setClickable(true);
            }else{
                Log.d("ERROR: ", "Unable to get image for uploading");
            }

        }
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        messageList = (ArrayList<Message>) resultData.getSerializable("data");
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(messageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
