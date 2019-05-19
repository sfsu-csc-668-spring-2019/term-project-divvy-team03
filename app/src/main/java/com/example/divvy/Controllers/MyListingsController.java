package com.example.divvy.Controllers;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.divvy.GetListingsService;
import com.example.divvy.NetworkReceiver;
import com.example.divvy.R;
import com.example.divvy.models.Listing;
import com.example.divvy.models.RecyclerViewAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MyListingsController extends DisplayListingsController{
    private NetworkReceiver mReceiver;
    public final String MY_LISTING_CODE = "mylistings";
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        mReceiver = new NetworkReceiver(new Handler(Looper.getMainLooper()),this);
        setContentView(R.layout.listings_view);
        NavBarController.setUpListners(findViewById(R.id.navigation), this);
        if(intent.getSerializableExtra("data") != null) {
            listings = (ArrayList<Listing>) intent.getSerializableExtra("data");
        }

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(listings);
        recyclerView = findViewById(R.id.listings_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void UpdateListingsView() {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(listings);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoginAuthenticator authenticator = LoginAuthenticator.getInstance();
        GetListingsService.GetListingsByUsername(this,mReceiver,authenticator.getUser(this));
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        listings = (ArrayList<Listing>)resultData.get("data");
        UpdateListingsView();
    }
}
