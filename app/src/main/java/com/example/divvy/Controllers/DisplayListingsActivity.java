package com.example.divvy.Controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.divvy.Listing;
import com.example.divvy.R;
import com.example.divvy.models.User;

import java.util.ArrayList;

public class DisplayListingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Listing> listings = new ArrayList<>();
        Listing listing = new Listing("Test Listing", "this is a test listing", 3,
                new User (), "open", 1);
        listings.add(listing);
        listing = new Listing("Test Listing 2", "this is a test listing", 3,
                new User (), "open", 2);
        listings.add(listing);
        listing = new Listing("Test Listing 3", "this is a test listing", 3,
                new User (), "open", 3);
        listings.add(listing);
        ListingListAdapter adapter = new ListingListAdapter(listings);

        setContentView(R.layout.listings_view);
        RecyclerView recyclerView = findViewById(R.id.listings_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
