package com.example.divvy.Controllers;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.divvy.NetworkReceiver;
import com.example.divvy.R;
import com.example.divvy.models.Listing;
import com.example.divvy.models.RecyclerViewAdapter;

import java.util.ArrayList;


import java.util.ArrayList;

public abstract class DisplayListingsController extends AppCompatActivity implements NetworkReceiver.GetListingsReceiver{
    ArrayList<Listing> listings;
    NetworkReceiver mReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listings = new ArrayList<>();
        mReceiver = new NetworkReceiver(new Handler(Looper.getMainLooper()), this);
        UpdateListingsView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Bundle savedState = new Bundle();
        savedState.putSerializable("data",listings);
        this.onSaveInstanceState(savedState);

    }
    public void UpdateListingsView(){
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(listings);
        RecyclerView recyclerView = findViewById(R.id.listings_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        finish();
        startActivity(getIntent());
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        listings = (ArrayList<Listing>)resultData.getSerializable("data");
        System.out.println("Array Size:" + listings.size());
        UpdateListingsView();
    }
}
