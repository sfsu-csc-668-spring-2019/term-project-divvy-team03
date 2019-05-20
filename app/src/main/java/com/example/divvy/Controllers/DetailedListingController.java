package com.example.divvy.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.divvy.R;
import com.example.divvy.models.Listing;

import java.util.ArrayList;

public class DetailedListingController extends AppCompatActivity {

    Listing listing;
    TextView titleText, descText, ownerText;
    ImageView ownerImg;
    Button messagingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        listing = ((ArrayList<Listing>)intent.getSerializableExtra("data")).get(0);
        System.out.println("Detailed listings: " + listing.toString());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_listings_controller);
        setUI();
        setUpListeners();
    }

    public void setUI(){
        titleText = findViewById(R.id.title);
        descText = findViewById(R.id.description);
        ownerText = findViewById(R.id.owner);
        ownerImg = findViewById(R.id.image);
        messagingButton = findViewById(R.id.messaging_button);
        NavBarController.setUpListners(findViewById(R.id.navigation), this);

        if(listing != null){
            titleText.setText(listing.getTitle());
            descText.setText(listing.getDescr());
            ownerText.setText(listing.getOwner());
        }
    }

    public void setUpListeners(){
        messagingButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, MessagingController.class);
            intent.putExtra("id", listing.getListingid());
            startActivity(intent);
        });
    }
}
