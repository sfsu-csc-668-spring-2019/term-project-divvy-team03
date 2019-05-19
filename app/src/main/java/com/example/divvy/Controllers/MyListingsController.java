package com.example.divvy.Controllers;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.divvy.ListingService;
import com.example.divvy.NetworkReceiver;
import com.example.divvy.R;
import com.example.divvy.models.Listing;
import com.example.divvy.models.RecyclerViewAdapter;

import java.util.ArrayList;

public class MyListingsController extends DisplayListingsController implements NetworkReceiver.DataReceiver{
    private NetworkReceiver mReceiver;
    public final String MY_LISTING_CODE = "mylistings";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        if(listings == null) {
            listings = new ArrayList<>();
        }
        mReceiver = new NetworkReceiver(null,this);
        setContentView(R.layout.listings_view);
        if(intent.getSerializableExtra("data") != null) {
            listings = (ArrayList<Listing>) intent.getSerializableExtra("data");
        }
        UpdateListings();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoginAuthenticator authenticator = LoginAuthenticator.getInstance();
        ListingService.GetListingsByUsername(this,mReceiver,authenticator.getUser(this));
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        listings = (ArrayList<Listing>)resultData.get("data");
        UpdateListings();
    }

    private void UpdateListings(){
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(listings);
        RecyclerView recyclerView = findViewById(R.id.listings_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

}
