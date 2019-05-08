package com.example.divvy.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.divvy.R;

public class MainActivity extends AppCompatActivity {

    public static String USERNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button createListingButton = findViewById(R.id.create_listing_btn);
        createListingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCreateListingActivity();
            }
        });
    }
    private void startCreateListingActivity(){
        Intent i = new Intent(this, CreateListing.class);
        startActivity(i);
    }
}
