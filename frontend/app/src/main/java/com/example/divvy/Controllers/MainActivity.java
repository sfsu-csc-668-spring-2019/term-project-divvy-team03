package com.example.divvy.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.divvy.Controllers.Services.ListingService;
import com.example.divvy.Controllers.Services.NetworkReceiver;
import com.example.divvy.Controllers.helpers.LoginAuthenticator;
import com.example.divvy.R;


public class MainActivity extends AppCompatActivity implements NetworkReceiver.DataReceiver {

    public static String USERNAME;
    public NetworkReceiver mReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mReceiver = new NetworkReceiver(null, this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //REMOVE THIS AFTER TESTING
        LogInFakeUser();
        Button createListingButton = findViewById(R.id.create_listing_btn);
        createListingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCreateListingActivity();
            }
        });
        Button displayListings = findViewById(R.id.display_listings_btn);
        displayListings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListingService.GetListingsByUsername(MainActivity.this,mReceiver, LoginAuthenticator.getInstance().getUser(MainActivity.this));
            }
        });
        Button search = findViewById(R.id.go_to_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchController.class);
                startActivity(intent);
            }
        });

        Button cancel = findViewById(R.id.register_cancel_btn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDisplayUser();
            }
        });

        Button messaging = findViewById(R.id.go_to_msg_btn);
        messaging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMessagingActivity();
            }
        });

    }
    private void startCreateListingActivity(){
        Intent i = new Intent(this, CreateListingController.class);
        startActivity(i);
    }

    private void startDisplayListings(){
        Intent i = new Intent(this, MyListingsController.class);
        startActivity(i);
    }

    private void startMessagingActivity(){
        Intent i = new Intent(this, MessagingController.class);
        startActivity(i);
    }

    private void startDisplayUser(){
        Intent i = new Intent(this, UserProfileViewController.class);
        i.putExtra("owner", "anton");
        startActivity(i);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        Intent i = new Intent(this, MyListingsController.class);
        i.putExtra("data", resultData.getSerializable("data"));
        startActivity(i);
    }

    public void LogInFakeUser(){
        LoginAuthenticator authenticator = LoginAuthenticator.getInstance();
        authenticator.LogInUser("alex", this);
    }
}
