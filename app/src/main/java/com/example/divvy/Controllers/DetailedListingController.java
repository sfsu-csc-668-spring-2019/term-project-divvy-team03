package com.example.divvy.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.divvy.NetworkReceiver;
import com.example.divvy.R;
import com.example.divvy.models.Listing;

public class DetailedListingController extends AppCompatActivity {

    Listing listing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        listing = (Listing)intent.getSerializableExtra("listing");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_listings_controller);
    }
}
